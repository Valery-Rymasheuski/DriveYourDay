package com.example.app.driveyourday.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "dyd_timer", foreignKeys = [ForeignKey(
        entity = DriveTimerGroupEntity::class,
        onDelete = CASCADE,
        parentColumns = arrayOf("group_id"),
        childColumns = arrayOf("group_id"),
    )]
)
data class DriveTimerEntity(
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "color") val color: Long,
    @ColumnInfo(name = "group_id") var groupId: Long = 0,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_id") val id: Long = 0,
)