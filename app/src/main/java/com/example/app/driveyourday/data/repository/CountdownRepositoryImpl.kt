package com.example.app.driveyourday.data.repository

import com.example.app.driveyourday.data.ds.CountdownLocalDataSource
import com.example.app.driveyourday.data.mappers.CountdownMapper
import com.example.app.driveyourday.data.util.ENTITY_EMPTY_ID
import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.di.modules.IoDispatcher
import com.example.app.driveyourday.domain.model.Countdown
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.repository.CountdownRepository
import com.example.app.driveyourday.util.getCurrentMillis
import com.example.app.driveyourday.util.toMillisFromMinutes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountdownRepositoryImpl @Inject constructor(
    private val localDataSource: CountdownLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val countdownMapper: CountdownMapper,
) : CountdownRepository {

    override suspend fun getAll(): List<Countdown> = withContext(ioDispatcher) {
        localDataSource.getAll().map(countdownMapper::fromEntity)
    }

    override suspend fun insert(countdown: Countdown): EntityId = withContext(ioDispatcher) {
        localDataSource.insert(countdownMapper.toEntity(countdown))
    }

    override suspend fun startTimer(timer: DriveTimer) = withContext(ioDispatcher) {
        val countdown = Countdown(
            id = ENTITY_EMPTY_ID,
            timerId = timer.id,
            durationMillis = timer.minutes.toLong().toMillisFromMinutes(),
            startedTimeMillis = getCurrentMillis(),
            name = timer.label,
        )
        localDataSource.insert(countdownMapper.toEntity(countdown))
    }
}