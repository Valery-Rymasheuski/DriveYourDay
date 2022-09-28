package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.domain.model.DriveTimerGroup

interface DriveTimerGroupsRepository {

    suspend fun getGroupsWithTimers(): List<DriveTimerGroup>
}