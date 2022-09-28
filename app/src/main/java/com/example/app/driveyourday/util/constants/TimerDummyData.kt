package com.example.app.driveyourday.util.constants

import androidx.compose.ui.graphics.Color
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.model.DriveTimerGroup

val dummyTimerGroups = listOf(
    DriveTimerGroup(
        1, "Job", 1, listOf(
            DriveTimer(1, "Working time", Color.Green),
            DriveTimer(2, "Relax time", Color.Red),
        )

    ),
    DriveTimerGroup(
        2, "Kitchen", 2, listOf(
            DriveTimer(3, "Cooking time", Color.Magenta)
        )
    )
)