package com.example.app.driveyourday.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTimerListViewModel @Inject constructor(private val timersRepository: DriveTimersRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(EditTimerListUiState())
    val uiState: StateFlow<EditTimerListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val timers = timersRepository.getTimers()
            _uiState.update { it.copy(timers = timers) }
        }
    }
}