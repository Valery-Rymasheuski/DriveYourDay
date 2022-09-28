package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity

@Dao
interface TimerGroupDao {

    @Query("SELECT * FROM dyd_timer_group")
    fun getGroups(): List<DriveTimerGroupEntity>
}