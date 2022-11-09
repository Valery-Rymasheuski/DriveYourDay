package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.TimerGroupDao
import com.example.app.driveyourday.data.local.entity.TimerGroupWithTimers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimerGroupsLocalDataSource @Inject constructor(private val dao: TimerGroupDao) {

    suspend fun getAll() = dao.getAll()

    suspend fun getGroupsWithTimers() = dao.getGroupsWithTimers()

    fun observeGroupsWithTimers(): Flow<List<TimerGroupWithTimers>> = dao.observeGroupsWithTimers()

}