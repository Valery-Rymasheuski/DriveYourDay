package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import kotlinx.coroutines.flow.Flow

interface DriveTimerGroupsRepository {

    suspend fun getGroupsWithTimers(): List<DriveTimerGroup>

    fun observeGroupsWithTimers() : Flow<List<DriveTimerGroup>>

    suspend fun getSimpleGroups() : List<DriveTimerGroupSimple>
}