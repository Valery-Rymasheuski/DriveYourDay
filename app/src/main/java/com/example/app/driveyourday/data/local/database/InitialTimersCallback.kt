package com.example.app.driveyourday.data.local.database

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app.driveyourday.util.constants.dummyTimerGroupEntities
import kotlinx.coroutines.*
import javax.inject.Provider

class InitialTimersCallback(
    private val dbProvider: Provider<DriveYourDayDatabase>,
    private val ioDispatcher: CoroutineDispatcher,
    private val applicationScope: CoroutineScope,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        applicationScope.launch(ioDispatcher) {//TODO create splash screen to wait for db initialization
            populateDatabase(dbProvider)
        }
    }

    private suspend fun populateDatabase(dbProvider: Provider<DriveYourDayDatabase>) {
        val db = dbProvider.get()
        db.withTransaction {
            val dao = db.timerDao()
            dummyTimerGroupEntities.forEach { (group, timers) ->
                dao.insert(group, timers)
            }
        }
    }
}