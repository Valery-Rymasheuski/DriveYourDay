package com.example.app.driveyourday.data.local.entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dyd_timer")
data class DriveTimerEntity(
    @ColumnInfo(name = "label") val label: String,
    //@ColumnInfo(name = "color") val color: Color,
    @ColumnInfo(name = "group_id") val groupId: Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_id") val id: Int = 0,
)