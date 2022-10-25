package com.example.app.driveyourday.ui.screens

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.R
import com.example.app.driveyourday.data.util.ENTITY_EMPTY_ID
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import com.example.app.driveyourday.util.NamedColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = AddTimerViewModel::class.simpleName

@HiltViewModel
class AddTimerViewModel @Inject constructor(
    private val timersRepository: DriveTimersRepository,
    private val timerGroupsRepository: DriveTimerGroupsRepository
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(AddTimerUiState(colors = getColors()))
    val uiState: StateFlow<AddTimerUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val groups = timerGroupsRepository.getSimpleGroups()
            _uiState.update { it.copy(groups = groups) }
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

    fun save(successNavigate: () -> Unit) {
        viewModelScope.launch { //TODO add ifStarted
            uiState.value.apply {
                if (isFieldsValid()) {
                    timersRepository.save(
                        DriveTimer(
                            ENTITY_EMPTY_ID,
                            timerName,
                            selectedColor!!.color,
                            selectedTimerGroup!!.id
                        )
                    )
                    successNavigate()
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