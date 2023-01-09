package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.TimerGroupDao
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity
import com.example.app.driveyourday.data.local.entity.TimerGroupWithTimers
import com.example.app.driveyourday.data.util.EntityId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimerGroupsLocalDataSource @Inject constructor(private val dao: TimerGroupDao) {

    suspend fun getAll() = dao.getAll()

    suspend fun getGroupsWithTimers() = dao.getGroupsWithTimers()

    fun observeGroupsWithTimers(): Flow<List<TimerGroupWithTimers>> = dao.observeGroupsWithTimers()

    suspend fun delete(group: DriveTimerGroupEntity) {
        dao.delete(group)
    }

    suspend fun update(group: DriveTimerGroupEntity) {
        dao.update(group)
    }

    suspend fun insert(group: DriveTimerGroupEntity) =
        dao.insert(group)

    suspend fun getById(id: EntityId) = dao.getById(id)

}