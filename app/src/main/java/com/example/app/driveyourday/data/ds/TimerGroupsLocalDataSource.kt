package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.TimerGroupDao
import javax.inject.Inject

class TimerGroupsLocalDataSource @Inject constructor(private val dao: TimerGroupDao) {

    suspend fun getAll() = dao.getAll()

}