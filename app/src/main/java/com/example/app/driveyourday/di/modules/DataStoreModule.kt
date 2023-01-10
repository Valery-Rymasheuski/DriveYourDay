package com.example.app.driveyourday.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.app.driveyourday.data.repository.UserPreferencesRepositoryImpl
import com.example.app.driveyourday.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES_FILE_NAME = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            appContext.preferencesDataStoreFile(
                USER_PREFERENCES_FILE_NAME
            )
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreRepoModule {

    @Binds
    abstract fun bindsUserPreferencesRepository(repository: UserPreferencesRepositoryImpl): UserPreferencesRepository
}