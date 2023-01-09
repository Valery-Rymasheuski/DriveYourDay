package com.example.app.driveyourday.ui.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.data.util.ENTITY_EMPTY_ID
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import com.example.app.driveyourday.ui.navigation.ARG_TIMER_GROUP_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AddTimerGroupViewModel"

@HiltViewModel
class AddTimerGroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupsRepository: DriveTimerGroupsRepository
) :
    ViewModel() {

    private val editedGroupId = savedStateHandle.get<String>(ARG_TIMER_GROUP_ID)?.toLong()

    private val _uiState = MutableStateFlow(AddTimerGroupUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d(TAG, "editedGroupId=$editedGroupId")
        if (editedGroupId != null) {
            viewModelScope.launch {
                val group = groupsRepository.getById(editedGroupId)
                requireNotNull(group)
                _uiState.update {
                    it.copy(
                        name = group.name,
                        editedId = editedGroupId
                    )
                }
            }
        }
    }

    fun updateGroupName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun save(onSuccessAdd: () -> Unit) {
        viewModelScope.launch {
            with(uiState.value) {
                if (isFieldsValid()) {
                    val entityId = editedId ?: ENTITY_EMPTY_ID
                    val group = DriveTimerGroupSimple(entityId, name, 1)
                    groupsRepository.save(group)
                    onSuccessAdd()
                } else {
                    Log.w(TAG, "Save skipped because fields are not valid.")
                }
            }
        }
    }
}