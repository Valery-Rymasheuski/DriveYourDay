package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity

@Dao
interface TimerDao {

    @Insert
    suspend fun insert(group: DriveTimerGroupEntity): Long

    @Insert
    suspend fun insert(vararg timers: DriveTimerEntity)

    suspend fun insert(group: DriveTimerGroupEntity, vararg timers: DriveTimerEntity) {
        val groupId = if (group.id <= 0) { //TODO
            insert(group)
        } else {
            group.id
        }

        val list = timers.map { it.copy(groupId = groupId) }
        insert(*list.toTypedArray())
    }

    @Update
    suspend fun update(timer: DriveTimerEntity)

    @Delete
    suspend fun delete(timer: DriveTimerEntity)

    @Query("SELECT * FROM dyd_timer")
    suspend fun getAll(): List<DriveTimerEntity>

    @Query("SELECT * FROM dyd_timer WHERE timer_id = :id")
    suspend fun getById(id: Long): DriveTimerEntity?
}