package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity

@Dao
interface TimerGroupDao {

    @Insert
    suspend fun insert(group: DriveTimerGroupEntity): Long

    @Update
    suspend fun update(group: DriveTimerGroupEntity)

    @Delete
    suspend fun delete(group: DriveTimerGroupEntity)

    @Query("SELECT * FROM dyd_timer_group")
    suspend fun getAll(): List<DriveTimerGroupEntity>
}