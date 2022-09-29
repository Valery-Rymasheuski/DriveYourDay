package com.example.app.driveyourday.di.modules

import android.content.Context
import com.example.app.driveyourday.data.local.dao.TimerDao
import com.example.app.driveyourday.data.local.dao.TimerGroupDao
import com.example.app.driveyourday.data.local.database.DriveYourDayDatabase
import com.example.app.driveyourday.data.local.database.InitialTimersCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Provider
import javax.inject.Singleton

const val DATABASE_NAME = "dyd-db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @ApplicationScope externalScope: CoroutineScope,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        timerDaoProvider: Provider<TimerDao>
    ): DriveYourDayDatabase {
        return androidx.room.Room.databaseBuilder(
            context,
            DriveYourDayDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addCallback(InitialTimersCallback(timerDaoProvider, ioDispatcher, externalScope))
            .build()
    }

    @Provides
    fun provideTimerDao(db: DriveYourDayDatabase): TimerDao = db.timerDao()

    @Provides
    fun provideTimerGroupDao(db: DriveYourDayDatabase): TimerGroupDao = db.timerGroupDao()

}
