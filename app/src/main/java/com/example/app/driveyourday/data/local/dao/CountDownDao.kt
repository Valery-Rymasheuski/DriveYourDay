package com.example.app.driveyourday.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.app.driveyourday.data.local.entity.CountdownEntity

@Dao
interface CountDownDao : BaseDao<CountdownEntity> {

    @Query("SELECT * FROM dyd_countdown")
    suspend fun getAll(): List<CountdownEntity>
}