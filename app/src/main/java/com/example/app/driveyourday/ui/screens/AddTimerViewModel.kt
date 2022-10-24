package com.example.app.driveyourday.ui.screens

import androidx.lifecycle.ViewModel
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddTimerViewModel @Inject constructor(private val timersRepository: DriveTimersRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(AddTimerUiState())
    val uiState: StateFlow<AddTimerUiState> = _uiState.asStateFlow()
}