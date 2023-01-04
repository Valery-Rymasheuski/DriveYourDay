package com.example.app.driveyourday.di.modules

import com.example.app.driveyourday.ui.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesNavigationManager() = NavigationManager()
}