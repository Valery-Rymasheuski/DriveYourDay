package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.Countdown
import com.example.app.driveyourday.domain.model.DriveTimer

interface CountdownRepository {

    suspend fun getAll(): List<Countdown>

    suspend fun insert(entity: Countdown): EntityId

    suspend fun startTimer(timer: DriveTimer): EntityId

    suspend fun findById(id: EntityId): Countdown?
}