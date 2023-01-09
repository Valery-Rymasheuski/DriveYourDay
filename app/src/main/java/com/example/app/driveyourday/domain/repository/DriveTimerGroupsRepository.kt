package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import kotlinx.coroutines.flow.Flow

interface DriveTimerGroupsRepository {

    suspend fun getGroupsWithTimers(): List<DriveTimerGroup>

    fun observeGroupsWithTimers() : Flow<List<DriveTimerGroup>>

    suspend fun getSimpleGroups() : List<DriveTimerGroupSimple>

    suspend fun delete(group: DriveTimerGroupSimple)

    suspend fun save(group: DriveTimerGroupSimple)

    suspend fun getById(id: EntityId) : DriveTimerGroupSimple?
}