package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.domain.model.DriveTimerGroup

data class HomeUiState(
    val isLoading: Boolean = false,
    val timerGroups: List<DriveTimerGroup> = emptyList(),
)

