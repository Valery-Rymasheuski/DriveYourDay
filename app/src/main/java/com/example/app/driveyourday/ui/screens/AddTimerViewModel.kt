package com.example.app.driveyourday.ui.screens

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.R
import com.example.app.driveyourday.data.util.ENTITY_EMPTY_ID
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import com.example.app.driveyourday.ui.navigation.ARG_TIMER_ID
import com.example.app.driveyourday.util.NamedColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = AddTimerViewModel::class.simpleName

@HiltViewModel
class AddTimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val timersRepository: DriveTimersRepository,
    private val timerGroupsRepository: DriveTimerGroupsRepository
) : ViewModel() {

    private val editTimerIdStr: String? = savedStateHandle[ARG_TIMER_ID]

    private val _uiState = MutableStateFlow(AddTimerUiState(colors = getColors()))
    val uiState: StateFlow<AddTimerUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val groups = timerGroupsRepository.getSimpleGroups()
            _uiState.update { it.copy(groups = groups) }

            if (editTimerIdStr != null) {
                val editedTimer = timersRepository.getById(editTimerIdStr.toLong())
                requireNotNull(editedTimer) //TODO
                _uiState.update { oldState ->
                    oldState.copy(timerName = editedTimer.label,
                        editedId = editedTimer.id,
                        selectedColor = oldState.colors.firstOrNull { color -> color.color == editedTimer.color },
                        selectedTimerGroup = oldState.groups.firstOrNull { g -> g.id == editedTimer.groupId }
                    )
                }
            }
        }
    }


    fun updateTimerName(name: String) {
        _uiState.update { it.copy(timerName = name) }
    }

    fun updateSelectedGroup(group: DriveTimerGroupSimple) {
        _uiState.update { it.copy(selectedTimerGroup = group) }
    }

    fun updateSelectedColor(color: NamedColor) {
        _uiState.update { it.copy(selectedColor = color) }
    }

    fun save(
        onNavigateToHome: () -> Unit,
        onNavigateToEditTimerList: () -> Unit,
        onSuccessAdd: (DriveTimer) -> Unit,
    ) {
        viewModelScope.launch {
            uiState.value.run {
                if (isFieldsValid()) {
                    val id = editedId ?: ENTITY_EMPTY_ID
                    val timer = DriveTimer(
                        id,
                        timerName,
                        selectedColor!!.color,
                        selectedTimerGroup!!.id
                    )
                    timersRepository.save(timer)
                    if (editedId == null) {
                        onSuccessAdd(timer)
                        onNavigateToHome()
                    } else {
                        onNavigateToEditTimerList()
                    }
                } else {
                    Log.w(TAG, "Save failed because fields are not valid.")
                }
            }
        }
    }

    private fun getColors(): List<NamedColor> = listOf(
        NamedColor(Color.Red, R.string.color_red),
        NamedColor(Color.Green, R.string.color_green),
        NamedColor(Color.Blue, R.string.color_blue),
    )
}