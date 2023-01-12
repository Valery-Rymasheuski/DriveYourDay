package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.app.driveyourday.data.local.entity.CountdownEntity
import com.example.app.driveyourday.data.util.EntityId

@Dao
interface CountDownDao : BaseDao<CountdownEntity> {

    @Query("SELECT * FROM dyd_countdown")
    suspend fun getAll(): List<CountdownEntity>

    @Query("SELECT * FROM dyd_countdown WHERE countdown_id = :id")
    suspend fun getById(id: EntityId): CountdownEntity?
}