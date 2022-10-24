package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.domain.model.DriveTimer

data class EditTimerListUiState(
    val isLoading: Boolean = false,
    val timers: List<DriveTimer> = emptyList(),
)