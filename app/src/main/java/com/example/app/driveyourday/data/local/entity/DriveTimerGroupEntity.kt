package com.example.app.driveyourday.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dyd_timer_group")
data class DriveTimerGroupEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order_number") val orderNumber: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id") val id: Int = 0
)