package com.example.app.driveyourday.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    suspend fun insert(obj: T): Long

    @Insert
    suspend fun insert(vararg obj: T)

    @Update
    suspend fun update(obj: T)

    @Delete
    suspend fun delete(obj: T)
}