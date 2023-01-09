package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity
import com.example.app.driveyourday.data.local.entity.TimerGroupWithTimers
import com.example.app.driveyourday.data.util.EntityId
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerGroupDao : BaseDao<DriveTimerGroupEntity> {

    @Query("SELECT * FROM dyd_timer_group")
    suspend fun getAll(): List<DriveTimerGroupEntity>

    @Transaction
    @Query("SELECT * FROM dyd_timer_group")
    suspend fun getGroupsWithTimers(): List<TimerGroupWithTimers>

    @Transaction
    @Query("SELECT * FROM dyd_timer_group")
    fun observeGroupsWithTimers(): Flow<List<TimerGroupWithTimers>>

    @Query("SELECT * FROM dyd_timer_group WHERE group_id = :id")
    suspend fun getById(id: EntityId): DriveTimerGroupEntity?
}