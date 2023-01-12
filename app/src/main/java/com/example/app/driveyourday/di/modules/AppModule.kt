package com.example.app.driveyourday.di.modules

import android.app.AlarmManager
import android.content.Context
import com.example.app.driveyourday.ui.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesNavigationManager() = NavigationManager()

    @Provides
    fun provideAlarmManager(@ApplicationContext ctx: Context) =
        ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
}