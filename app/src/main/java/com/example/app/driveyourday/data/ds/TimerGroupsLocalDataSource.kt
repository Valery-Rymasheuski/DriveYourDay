package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.TimerGroupDao
import com.example.app.driveyourday.data.local.entity.TimerGroupWithTimers
import javax.inject.Inject

class TimerGroupsLocalDataSource @Inject constructor(private val dao: TimerGroupDao) {

    suspend fun getAll() = dao.getAll()

    suspend fun getGroupWithTimers() = dao.getGroupWithTimers()

}