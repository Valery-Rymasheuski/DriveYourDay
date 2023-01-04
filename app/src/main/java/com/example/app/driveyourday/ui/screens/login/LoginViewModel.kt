package com.example.app.driveyourday.ui.screens.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.domain.repository.login.LoginRepository
import com.example.app.driveyourday.ui.navigation.NavigationCommand
import com.example.app.driveyourday.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"
private const val SAVED_STATE_FILED_DATA = "filledData"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    /**
     * Holds the current state of a step
     */
    private val stepData =
        MutableSharedFlow<Outcome<LoginStep>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    private val filledData = MutableSharedFlow<LoginFilledData>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val currentStepId = MutableSharedFlow<LoginNavId>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _enterOptionsStep = MutableStateFlow<Outcome<LoginStep.EnterOptionsStep>>(
        Outcome.Loading(
            LoginStep.EnterOptionsStep(persistentListOf())
        )
    )
    val enterOptionsStep = _enterOptionsStep.asStateFlow()

    private val _enterEmailStep = MutableStateFlow<Outcome<LoginStep.EnterEmailStep>>(
        Outcome.Loading(LoginStep.EnterEmailStep(null)) //TODO
    )
    val enterEmailStep = _enterEmailStep.asStateFlow()

    private val _enterPasswordStep = MutableStateFlow<Outcome<LoginStep.EnterPasswordStep>>(
        Outcome.Loading(
            LoginStep.EnterPasswordStep()
        )
    )
    val enterPasswordStep = _enterPasswordStep.asStateFlow()

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
        Log.i(TAG, "init $TAG")
        filledData.tryEmit(currentFilledData) //TODO

        viewModelScope.launch {
            filledData.collectLatest { data ->
                //store data to survive recreation
                savedInstanceData = data
                onDataProvided(data)
            }
        }
        initNavigation()

        viewModelScope.launch {
            stepData.collect { outcome ->
                @Suppress("UNCHECKED_CAST")
                when (outcome.data.id) {
                    LoginNavId.LOGIN_END -> {
                        //do nothing
                    }
                    LoginNavId.LOGIN_OPTIONS -> _enterOptionsStep.emit(outcome as Outcome<LoginStep.EnterOptionsStep>)
                    LoginNavId.LOGIN_EMAIL -> _enterEmailStep.emit(outcome as Outcome<LoginStep.EnterEmailStep>)
                    LoginNavId.LOGIN_PASSWORD -> _enterPasswordStep.emit(outcome as Outcome<LoginStep.EnterPasswordStep>)
                }
            }
        }
    }

    private fun initNavigation() {
        viewModelScope.launch {
            currentStepId.collect { step ->
                val stepName = step.name
                when (step) {
                    LoginNavId.LOGIN_OPTIONS -> {
                        navigationManager.navigate(
                            NavigationCommand(
                                stepName,
                                launchSingleTop = true,
                                inclusive = true,
                                skipSameRoute = true,
                            )
                        )
                    }
                    LoginNavId.LOGIN_EMAIL -> {
                        navigationManager.navigate(
                            NavigationCommand(
                                stepName,
                                launchSingleTop = true
                            )
                        )
                    }
                    LoginNavId.LOGIN_PASSWORD -> {
                        navigationManager.navigate(
                            NavigationCommand(
                                stepName,
                                launchSingleTop = true
                            )
                        )
                    }
                    LoginNavId.LOGIN_END -> {
                        navigationManager.navigate(
                            NavigationCommand(
                                stepName,
                                launchSingleTop = true,
                                popUpTo = LoginNavId.LOGIN_OPTIONS.name,
                                inclusive = true
                            )
                        )
                    }
                }
            }
        }
    }

    /**
     * Notifies ViewModel that destination has changed so we can detect back navigation
     * and adjust uiState accordingly
     */
    fun onDestinationChanged(navId: LoginNavId) {
         viewModelScope.launch {
             val step = currentStepId.first()
             Log.i(TAG, "onDestinationChanged: step=$step, navId=$navId")
             if (step != navId) {
                 applyNewStep(navId, currentFilledData)
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
            } else {
                stepData.emit(Outcome.Error(LoginStep.EnterEmailStep(null), InvalidEmilException()))
            }
        }
    }

    fun onPasswordEntered(password: String) {
        viewModelScope.launch {

            stepData.emit(Outcome.Loading(LoginStep.EnterPasswordStep()))
            kotlin.runCatching {
                with(currentFilledData) {
                    val userId = loginRepository.login(userEmail!!, password)

                    filledData.emit(
                        currentFilledData.copy(
                            userPassword = password,
                            userId = userId
                        )
                    )
                    Log.i(TAG, "login result:  password=$password, userId=$userId")
                }
            }.onFailure {
                stepData.emit(Outcome.Error(LoginStep.EnterPasswordStep(), it))
            }
        }
    }

    private suspend fun applyNewStep(navId: LoginNavId, data: LoginFilledData) {
        Log.i(TAG, "applyNewStep: navId=$navId")
        currentStepId.emit(navId)

        val newStepData = when (navId) {
            LoginNavId.LOGIN_OPTIONS -> LoginStep.EnterOptionsStep(
                persistentListOf(
                    "Google",
                    "Twitter",
                    "Email"
                )
            )
            LoginNavId.LOGIN_EMAIL -> LoginStep.EnterEmailStep(data.userEmail)
            LoginNavId.LOGIN_PASSWORD -> LoginStep.EnterPasswordStep()
            LoginNavId.LOGIN_END -> LoginStep.End
        }
        stepData.emit(Outcome.Success(newStepData))

    }

    private suspend fun onDataProvided(data: LoginFilledData) {
        Log.i(TAG, "onDataProvided: data=$data")
        val nextNavId = with(data) {
            when {
                flowType == null -> LoginNavId.LOGIN_OPTIONS
                userEmail == null -> LoginNavId.LOGIN_EMAIL
                userPassword == null || userId == null -> LoginNavId.LOGIN_PASSWORD
                else -> LoginNavId.LOGIN_END
            }
        }
        return applyNewStep(nextNavId, data)
    }
}