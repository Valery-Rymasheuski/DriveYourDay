package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.TimerDao
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity
import javax.inject.Inject

class TimersLocalDataSource @Inject constructor(private val dao: TimerDao) {

    suspend fun getAll() = dao.getAll()

    suspend fun insert(group: DriveTimerGroupEntity, timers: List<DriveTimerEntity>) =
        dao.insert(group, timers)

    suspend fun insert(timer: DriveTimerEntity){
        dao.insert(timer)
    }

}