package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.DriveTimer

interface DriveTimersRepository {

    suspend fun getTimers(): List<DriveTimer>

    suspend fun save(timer: DriveTimer)

    suspend fun delete(timer: DriveTimer)

    suspend fun getById(id: EntityId): DriveTimer?
}