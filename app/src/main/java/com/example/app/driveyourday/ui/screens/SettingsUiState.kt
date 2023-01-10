package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.domain.model.UserPreferences

data class SettingsUiState(
    val isLoading: Boolean,
    val userPreferences: UserPreferences? = null,
)