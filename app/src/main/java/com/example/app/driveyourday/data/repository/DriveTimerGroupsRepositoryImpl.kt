package com.example.app.driveyourday.data.repository

import com.example.app.driveyourday.data.ds.TimerGroupsLocalDataSource
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import com.example.app.driveyourday.util.constants.dummyTimerGroups
import javax.inject.Inject

class DriveTimerGroupsRepositoryImpl @Inject constructor(private val localDataSource: TimerGroupsLocalDataSource) :
    DriveTimerGroupsRepository {

    override suspend fun getGroupsWithTimers(): List<DriveTimerGroup> {
        return localDataSource.getGroups()
        //return dummyTimerGroups //TODO change
    }
}