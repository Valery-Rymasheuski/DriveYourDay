package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.TimerGroupDao
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.util.constants.dummyTimerGroups
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TimerGroupsLocalDataSource @Inject constructor(private val dao: TimerGroupDao) {

    //TODO move dispatcher
    suspend fun getGroups(): List<DriveTimerGroup> = withContext(Dispatchers.IO){
        dao.getGroups()
         dummyTimerGroups
    }
}