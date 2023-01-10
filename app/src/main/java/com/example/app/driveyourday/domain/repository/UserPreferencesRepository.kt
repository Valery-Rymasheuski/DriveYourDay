package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val userPreferencesFlow: Flow<UserPreferences>

    val bottomNavigationFlow: Flow<Boolean>

    suspend fun updateBottomNavigation(value: Boolean)
}