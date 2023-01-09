package com.example.app.driveyourday.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EditTimerGroupsVM"

@HiltViewModel
class EditTimerGroupsViewModel @Inject constructor(private val timerGroupsRepository: DriveTimerGroupsRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(EditTimerGroupsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadGroups()
        }
    }

    private suspend fun loadGroups() {
        timerGroupsRepository.getSimpleGroups().also { groups ->
            _uiState.update { it.copy(groups = groups) }
        }
    }

    fun delete(id: EntityId) {
        val group = uiState.value.groups.firstOrNull { it.id == id }
        if (group != null) {
            viewModelScope.launch {
                timerGroupsRepository.delete(group)
                loadGroups()
            }
        } else {
            Log.w(TAG, "Timer group not found by id: $id")
        }
    }
}