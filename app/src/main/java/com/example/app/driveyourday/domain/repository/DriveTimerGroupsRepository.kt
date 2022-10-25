package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple

interface DriveTimerGroupsRepository {

    suspend fun getGroupsWithTimers(): List<DriveTimerGroup>

    suspend fun getSimpleGroups() : List<DriveTimerGroupSimple>
}