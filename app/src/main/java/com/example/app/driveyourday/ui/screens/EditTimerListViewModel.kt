package com.example.app.driveyourday.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = EditTimerListViewModel::class.simpleName

@HiltViewModel
class EditTimerListViewModel @Inject constructor(private val timersRepository: DriveTimersRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(EditTimerListUiState())
    val uiState: StateFlow<EditTimerListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch { //TODO
            loadTimers()
        }
    }

    private suspend fun loadTimers() {
        val timers = timersRepository.getTimers()
        _uiState.update { it.copy(timers = timers) }
    }

    fun delete(timerId: EntityId) {
        val timer = findById(timerId)
        if (timer != null) {
            viewModelScope.launch { //TODO
                timersRepository.delete(timer)
                loadTimers()
            }
        } else {
            Log.w(TAG, "Timer not found by id: $timerId")
        }
    }

    private fun findById(id: EntityId): DriveTimer? {
        return uiState.value.timers.firstOrNull { it.id == id }
    }
}