package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple

data class EditTimerGroupsUiState(
    val isLoading: Boolean = false,
    val groups: List<DriveTimerGroupSimple> = emptyList()
)