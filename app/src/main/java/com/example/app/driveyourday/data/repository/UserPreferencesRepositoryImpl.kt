package com.example.app.driveyourday.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.app.driveyourday.domain.model.UserPreferences
import com.example.app.driveyourday.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

private const val TAG = "UserPreferencesRepo"

class UserPreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {

    override val userPreferencesFlow = dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.e(TAG, "Error reading preferences.", e)
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { mapPreferences(it) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val bottomNavigationFlow = userPreferencesFlow.mapLatest { it.bottomNavigation }

    override suspend fun updateBottomNavigation(value: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.BOTTOM_NAVIGATION] = value
        }
    }

    private fun mapPreferences(prefs: Preferences): UserPreferences {
        val bottomNavigation = prefs[PreferencesKeys.BOTTOM_NAVIGATION] ?: false

        return UserPreferences(bottomNavigation = bottomNavigation)
    }

    private object PreferencesKeys {
        val BOTTOM_NAVIGATION = booleanPreferencesKey("bottom_navigation")
    }
}