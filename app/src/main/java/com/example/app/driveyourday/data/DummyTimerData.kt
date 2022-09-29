package com.example.app.driveyourday.util.constants

import androidx.compose.ui.graphics.Color
import com.example.app.driveyourday.data.local.entity.DriveTimerEntity
import com.example.app.driveyourday.data.local.entity.DriveTimerGroupEntity
import com.example.app.driveyourday.data.mappers.DriveTimerGroupMapper
import com.example.app.driveyourday.data.mappers.DriveTimerMapper
import com.example.app.driveyourday.data.mappers.mapColorToLong
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import java.util.UUID

val dummyTimerGroupEntities = listOf(
    DriveTimerGroupEntity(
        "Job", 1
    ) to listOf(
        DriveTimerEntity("Working time", Color.Green.mapColorToLong()),
        DriveTimerEntity("Relax time", Color.Red.mapColorToLong()),
    ),
    DriveTimerGroupEntity(
        "Kitchen", 2
    ) to listOf(
        DriveTimerEntity("Cooking time", Color.Magenta.mapColorToLong())
    )
)

//TODO optimize
fun getDummyTimerGroups(): List<DriveTimerGroup> {
    val groupMapper = DriveTimerGroupMapper(DriveTimerMapper())
    fun getId() = UUID.randomUUID().timestamp()
    return dummyTimerGroupEntities.map { (group, list) ->
        groupMapper.fromEntity(
            group.copy(id = getId()),
            list.map { it.copy(id = getId(), groupId = getId()) }
        )
    }
}