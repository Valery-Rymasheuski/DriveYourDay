package com.example.app.driveyourday.domain.model

import androidx.compose.ui.graphics.Color

data class DriveTimer(
    val id: Long,
    val label: String,
    val color: Color,
    val groupId: Long,
)