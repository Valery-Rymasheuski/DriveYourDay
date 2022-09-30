package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity
import com.example.app.driveyourday.data.util.isNotValidEntityId

@Dao
interface TimerDao : BaseDao<DriveTimerEntity> {

    @Insert
    suspend fun insert(group: DriveTimerGroupEntity): Long

    @Insert
    suspend fun insert(timers: List<DriveTimerEntity>)

    @Transaction
    suspend fun insert(group: DriveTimerGroupEntity, timers: List<DriveTimerEntity>) {
        val groupId = if (group.id.isNotValidEntityId()) {
            insert(group)
        } else {
            group.id
        }
        timers.forEach { it.groupId = groupId }
        insert(timers)
    }

    @Query("SELECT * FROM dyd_timer")
    suspend fun getAll(): List<DriveTimerEntity>

    @Query("SELECT * FROM dyd_timer WHERE timer_id = :id")
    suspend fun getById(id: Long): DriveTimerEntity?
}