package com.example.app.driveyourday.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = HomeViewModel::class.simpleName

@HiltViewModel
class HomeViewModel @Inject constructor(private val timerGroupsRepository: DriveTimerGroupsRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        Log.e(TAG, "Init")
        viewModelScope.launch {
            timerGroupsRepository.observeGroupsWithTimers().collect { groups ->
                Log.e(
                    TAG,
                    "Loaded timer groups with size ${groups.size} [${coroutineContext}]"
                )
                _uiState.update { it.copy(isLoading = false, timerGroups = groups) }
            }

            //   val groups = timerGroupsRepository.getGroupsWithTimers() //without data updates
            //   _uiState.update { it.copy(isLoading = false, timerGroups = groups) }
        }
    }

    fun setAddedTimerEvent(addedTimer: DriveTimer) {
        Log.e(TAG, "Added event, timer  = $addedTimer")
        _uiState.update { it.copy(addedTimerEvent = triggered(addedTimer.label)) }
    }

    fun setAddedTimerEventConsumed() {
        _uiState.update { it.copy(addedTimerEvent = consumed()) }
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(TAG, "onCleared")
    }
}