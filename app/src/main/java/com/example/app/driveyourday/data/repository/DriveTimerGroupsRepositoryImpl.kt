package com.example.app.driveyourday.data.repository

import com.example.app.driveyourday.data.ds.TimerGroupsLocalDataSource
import com.example.app.driveyourday.data.ds.TimersLocalDataSource
import com.example.app.driveyourday.data.mappers.DriveTimerGroupMapper
import com.example.app.driveyourday.data.mappers.DriveTimerMapper
import com.example.app.driveyourday.di.modules.IoDispatcher
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DriveTimerGroupsRepositoryImpl @Inject constructor(
    private val localGroupsDataSource: TimerGroupsLocalDataSource,
    private val localTimersDataSource: TimersLocalDataSource,
    private val groupMapper: DriveTimerGroupMapper,
    private val timerMapper: DriveTimerMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : DriveTimerGroupsRepository {

    override suspend fun getGroupsWithTimers(): List<DriveTimerGroup> =
        withContext(ioDispatcher) {

            localGroupsDataSource.getGroupWithTimers()
                .map { (group, timers) -> groupMapper.fromEntity(group, timers) }

            /* val groups = localGroupsDataSource.getAll() //TODO do in parallel
             val timers = localTimersDataSource.getAll()

             val timersMap = timers.groupBy { it.groupId }
             groups.map { groupMapper.fromEntity(it, timersMap.getOrElse(it.id) { emptyList() }) }*/
        }
}