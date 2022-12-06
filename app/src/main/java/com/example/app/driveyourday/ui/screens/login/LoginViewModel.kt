package com.example.app.driveyourday.ui.screens.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.domain.repository.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVED_STATE_FILED_DATA = "filledData"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    /**
     * Holds the current state of a login action
     */
    private val actionState = MutableStateFlow<Outcome<Unit>>(Outcome.Success(Unit))

    private val filledData = MutableSharedFlow<LoginFilledData>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val currentStep = MutableSharedFlow<LoginStep>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val uiState: Flow<Outcome<LoginStep>> =
        combine(actionState, currentStep) { actionStatus: Outcome<Unit>, step: LoginStep ->
            actionStatus.map { step }
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    private val currentFilledData: LoginFilledData
        get() = savedInstanceData ?: LoginFilledData(
            null, null,
            null, false, null
        )

    private var savedInstanceData: LoginFilledData?
        get() = savedStateHandle[SAVED_STATE_FILED_DATA]
        set(value) {
            savedStateHandle[SAVED_STATE_FILED_DATA] = value
        }

    init {
        filledData.tryEmit(currentFilledData) //TODO

        viewModelScope.launch {
            filledData.collectLatest { data ->

                //store data to survive recreation
                savedInstanceData = data
                val nextStep = onDataProvided(data)
                currentStep.emit(nextStep)
            }
        }
    }

    /**
     * Notifies ViewModel that destination has changed so we can detect back navigation
     * and adjust uiState accordingly
     */
    fun onDestinationChanged(navId: LoginNavId) {
        viewModelScope.launch {
            val step = currentStep.first()

            if (step.id != navId) {
                currentStep.tryEmit(generateStep(navId, currentFilledData))
            }
        }
    }

    /**
     * A Login option was selected by the user
     */
    fun onOptionSelected(option: String) {
        viewModelScope.launch {
            filledData.emit(currentFilledData.copy(flowType = LoginFlowType.EMAIL))
        }
    }

    fun onEmailEntered(email: String) {
        viewModelScope.launch {
            //TODO do validation
            if (email.contains('@')) {
                filledData.emit(currentFilledData.copy(userEmail = email))
                actionState.emit(Outcome.Success(Unit))
            } else {
                actionState.emit(Outcome.Error(Unit, InvalidEmilException()))
            }
        }
    }

    fun onPasswordEntered(password: String) {
        viewModelScope.launch {

            actionState.emit(Outcome.Loading(Unit))
            kotlin.runCatching {
                with(currentFilledData) {
                    loginRepository.login(userEmail!!, password)

                    actionState.emit(Outcome.Success(Unit))
                    filledData.emit(
                        currentFilledData.copy(
                            userPassword = password,
                            userId = userId
                        )
                    )
                }
            }.onFailure {
                actionState.emit(Outcome.Error(Unit, it))
            }
        }
    }

    private fun generateStep(navId: LoginNavId, data: LoginFilledData): LoginStep =
        when (navId) {
            LoginNavId.START -> LoginStep.Start
            LoginNavId.OPTIONS -> LoginStep.EnterOptionsStep(listOf("Google", "Twitter", "Email"))
            LoginNavId.EMAIL -> LoginStep.EnterEmailStep(data.userEmail)
            LoginNavId.PASSWORD -> LoginStep.EnterPasswordStep()
            LoginNavId.END -> LoginStep.End
        }

    private fun onDataProvided(data: LoginFilledData): LoginStep {

        val nextNavId = with(data) {
            when {
                flowType == null -> LoginNavId.OPTIONS
                userEmail == null -> LoginNavId.EMAIL
                userPassword == null || userId == null -> LoginNavId.PASSWORD
                else -> LoginNavId.END
            }
        }
        return generateStep(nextNavId, data)
    }
}