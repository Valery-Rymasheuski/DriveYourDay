package com.example.app.driveyourday.data.repository

import com.example.app.driveyourday.data.ds.TimersLocalDataSource
import com.example.app.driveyourday.data.mappers.DriveTimerMapper
import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.data.util.isValidEntityId
import com.example.app.driveyourday.di.modules.IoDispatcher
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DriveTimersRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localDataSource: TimersLocalDataSource,
    private val timerMapper: DriveTimerMapper,
) : DriveTimersRepository {

    override suspend fun getTimers(): List<DriveTimer> = withContext(ioDispatcher) {
        localDataSource.getAll().map { timerMapper.fromEntity(it) }
    }

    override suspend fun save(timer: DriveTimer) = withContext(ioDispatcher) {
        timerMapper.toEntity(timer).let { entity ->
            if (entity.id.isValidEntityId()) {
                localDataSource.update(entity)
            } else {
                localDataSource.insert(entity)
            }
        }
    }

    override suspend fun delete(timer: DriveTimer) = withContext(ioDispatcher) {
        localDataSource.delete(timerMapper.toEntity(timer))
    }

    override suspend fun getById(id: EntityId) = withContext(ioDispatcher) {
        localDataSource.getById(id)?.let { timerMapper.fromEntity(it) }
    }
}