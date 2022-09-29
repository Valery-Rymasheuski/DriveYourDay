package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity

@Dao
interface TimerDao : BaseDao<DriveTimerEntity>{

    @Insert
    suspend fun insert(group: DriveTimerGroupEntity): Long

    @Transaction
    suspend fun insert(group: DriveTimerGroupEntity, vararg timers: DriveTimerEntity) {
        val groupId = if (group.id <= 0) { //TODO
            insert(group)
        } else {
            group.id
        }

        val list = timers.map { it.copy(groupId = groupId) }
        insert(*list.toTypedArray())
    }

    @Query("SELECT * FROM dyd_timer")
    suspend fun getAll(): List<DriveTimerEntity>

    @Query("SELECT * FROM dyd_timer WHERE timer_id = :id")
    suspend fun getById(id: Long): DriveTimerEntity?
}