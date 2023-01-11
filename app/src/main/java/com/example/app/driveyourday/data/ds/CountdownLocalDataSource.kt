package com.example.app.driveyourday.data.ds

import com.example.app.driveyourday.data.local.dao.CountDownDao
import com.example.app.driveyourday.data.local.entity.CountdownEntity
import javax.inject.Inject

class CountdownLocalDataSource @Inject constructor(private val dao: CountDownDao) {

    suspend fun getAll() = dao.getAll()

    suspend fun insert(entity: CountdownEntity) = dao.insert(entity)

    suspend fun delete(entity: CountdownEntity) = dao.delete(entity)
}