package com.example.app.driveyourday.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.app.driveyourday.data.util.EntityId

@Entity(
    tableName = "dyd_countdown", foreignKeys = [ForeignKey(
        entity = DriveTimerEntity::class,
        parentColumns = arrayOf("timer_id"),
        childColumns = arrayOf("timer_id"),
    )]
)
data class CountdownEntity(
    @ColumnInfo(name = "timer_id") val timerId: EntityId,
    @ColumnInfo(name = "duration") val durationMillis: Long,
    @ColumnInfo(name = "started_time") val startedTimeMillis: Long,
    @ColumnInfo(name = "name") val name: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "countdown_id") val id: EntityId = 0,
)