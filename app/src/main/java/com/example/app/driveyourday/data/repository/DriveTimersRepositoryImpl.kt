package com.example.app.driveyourday.data.repository

import com.example.app.driveyourday.di.modules.IoDispatcher
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DriveTimersRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : DriveTimersRepository {

    override fun getTimers(): List<DriveTimer> {
        TODO("Not yet implemented")
    }
}