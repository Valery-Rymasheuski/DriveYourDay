package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.TimerDao
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity
import com.example.app.driveyourday.data.util.EntityId
import javax.inject.Inject

class TimersLocalDataSource @Inject constructor(private val dao: TimerDao) {

    suspend fun getAll() = dao.getAll()

    suspend fun insert(group: DriveTimerGroupEntity, timers: List<DriveTimerEntity>) =
        dao.insert(group, timers)

    suspend fun insert(timer: DriveTimerEntity) {
        dao.insert(timer)
    }

    suspend fun update(timer: DriveTimerEntity) {
        dao.update(timer)
    }

    suspend fun delete(timer: DriveTimerEntity) {
        dao.delete(timer)
    }

    suspend fun getById(id: EntityId): DriveTimerEntity? = dao.getById(id)

}