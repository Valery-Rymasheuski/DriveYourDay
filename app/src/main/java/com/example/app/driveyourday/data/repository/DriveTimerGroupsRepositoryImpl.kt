package com.example.app.driveyourday.data.repository

import com.example.app.driveyourday.data.ds.TimerGroupsLocalDataSource
import com.example.app.driveyourday.data.ds.TimersLocalDataSource
import com.example.app.driveyourday.data.mappers.DriveTimerGroupMapper
import com.example.app.driveyourday.data.mappers.DriveTimerMapper
import com.example.app.driveyourday.di.modules.IoDispatcher
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import kotlinx.coroutines.*
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

            getGroupsWithTimersUsingRelation()
           // getGroupsWithTimersParallel()
        }

    private suspend fun getGroupsWithTimersUsingRelation() =
        localGroupsDataSource.getGroupWithTimers()
            .map { (group, timers) -> groupMapper.fromEntity(group, timers) }

    private suspend fun getGroupsWithTimersParallel(): List<DriveTimerGroup> {
        return coroutineScope {
            val groupsDeferred = async { localGroupsDataSource.getAll() }
            val timersDeferred = async { localTimersDataSource.getAll() }
            val groups = groupsDeferred.await()
            val timers = timersDeferred.await()

            val timersMap = timers.groupBy { it.groupId }
            groups.map { groupMapper.fromEntity(it, timersMap.getOrElse(it.id) { emptyList() }) }
        }
    }
}