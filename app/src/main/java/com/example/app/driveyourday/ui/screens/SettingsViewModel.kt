package com.example.app.driveyourday.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2_000)
            userPreferencesRepository.userPreferencesFlow.collect { prefs ->
                _uiState.update { it.copy(isLoading = false, userPreferences = prefs) }
            }
        }
    }

    fun bottomNavigationChanged(value: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateBottomNavigation(value)
        }
    }
}