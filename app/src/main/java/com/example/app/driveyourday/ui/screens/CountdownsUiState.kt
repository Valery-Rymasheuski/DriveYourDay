package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.domain.model.Countdown

data class CountdownsUiState(
    val isLoading: Boolean,
    val countdowns: List<CountdownAtTimeInfo> = emptyList(),
)

data class CountdownAtTimeInfo(
    val countdown: Countdown,
    val leftMinutes: Long,
    val expired: Boolean,
)