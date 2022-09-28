package com.example.app.driveyourday.domain.model

data class DriveTimerGroup(
    val id: Int?,
    val name: String,
    val orderNumber: Int,
    val timers: List<DriveTimer>,
)