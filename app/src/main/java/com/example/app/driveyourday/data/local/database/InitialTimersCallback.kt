package com.example.app.driveyourday.data.local.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app.driveyourday.data.local.dao.TimerDao
import com.example.app.driveyourday.util.constants.dummyTimerGroupEntities
import kotlinx.coroutines.*
import javax.inject.Provider

class InitialTimersCallback(
    private val provider: Provider<TimerDao>,
    private val ioDispatcher: CoroutineDispatcher,
    private val applicationScope: CoroutineScope,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(ioDispatcher) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        //TODO withTransaction ?
        val dao = provider.get()
        dummyTimerGroupEntities.forEach { (group, list) ->
            dao.insert(group, *list.toTypedArray())
        }
    }
}