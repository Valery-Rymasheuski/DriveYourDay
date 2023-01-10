package com.example.app.driveyourday.domain.model

import androidx.compose.ui.graphics.Color
import com.example.app.driveyourday.data.util.EntityId

data class DriveTimer(
    val id: EntityId,
    val label: String,
    val color: Color,
    val groupId: EntityId,
    val minutes: Short,
)