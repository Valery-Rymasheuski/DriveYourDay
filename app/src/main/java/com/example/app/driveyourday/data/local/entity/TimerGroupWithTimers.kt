package com.example.app.driveyourday.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TimerGroupWithTimers(
    @Embedded val group: DriveTimerGroupEntity,
    @Relation(parentColumn = "group_id", entityColumn = "group_id")
    val groupTimers: List<DriveTimerEntity>
)