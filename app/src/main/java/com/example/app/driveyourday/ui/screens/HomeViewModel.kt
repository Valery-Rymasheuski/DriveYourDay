package com.example.app.driveyourday.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val timerGroupsRepository: DriveTimerGroupsRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val groups = timerGroupsRepository.getGroupsWithTimers()
            _uiState.update { it.copy(isLoading = false, timerGroups = groups) }
        }
    }
}