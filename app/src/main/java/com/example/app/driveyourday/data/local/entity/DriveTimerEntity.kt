package com.example.app.driveyourday.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dyd_timer")
data class DriveTimerEntity(
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "color") val color: Long,
    @ColumnInfo(name = "group_id") val groupId: Long = 0,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_id") val id: Long = 0,
)