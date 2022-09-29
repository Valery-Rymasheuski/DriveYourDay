package com.example.app.driveyourday.data.mappers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import javax.inject.Inject

class DriveTimerMapper @Inject constructor() : EntityMapper<DriveTimerEntity, DriveTimer> {

    override fun fromEntity(entity: DriveTimerEntity): DriveTimer =
        DriveTimer(entity.id, entity.label, entity.color.mapLongToColor(), entity.groupId)

    override fun toEntity(domain: DriveTimer): DriveTimerEntity =
        DriveTimerEntity(domain.label, domain.color.mapColorToLong(), domain.groupId, domain.id)
}


class DriveTimerGroupMapper @Inject constructor(private val timerMapper: DriveTimerMapper) {

    fun fromEntity(
        entity: DriveTimerGroupEntity,
        timerEntities: List<DriveTimerEntity>
    ): DriveTimerGroup =
        DriveTimerGroup(
            entity.id,
            entity.name,
            entity.orderNumber,
            timerMapper.fromEntityList(timerEntities)
        )

    fun toEntity(domain: DriveTimerGroup): DriveTimerGroupEntity =
        DriveTimerGroupEntity(domain.name, domain.orderNumber, domain.id)
}

fun Color.mapColorToLong(): Long = toArgb().toLong()

fun Long.mapLongToColor(): Color = Color(this)