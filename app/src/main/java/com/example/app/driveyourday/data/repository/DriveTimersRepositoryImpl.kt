package com.example.app.driveyourday.data.repository

import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DriveTimersRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DriveTimersRepository {

    override fun getTimers(): List<DriveTimer> {
        TODO("Not yet implemented")
    }
}