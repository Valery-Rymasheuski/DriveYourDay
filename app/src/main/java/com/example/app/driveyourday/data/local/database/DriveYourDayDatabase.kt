package com.example.app.driveyourday.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app.driveyourday.data.local.dao.CountDownDao
import com.example.app.driveyourday.data.local.dao.TimerDao
import com.example.app.driveyourday.data.local.dao.TimerGroupDao
import com.example.app.driveyourday.data.local.entity.CountdownEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity

@Database(
    entities = [DriveTimerEntity::class,
        DriveTimerGroupEntity::class,
        CountdownEntity::class], version = 1,
    exportSchema = false
)
abstract class DriveYourDayDatabase : RoomDatabase() {

    abstract fun timerDao(): TimerDao

    abstract fun timerGroupDao(): TimerGroupDao

    abstract fun countdownDao(): CountDownDao
}