package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.domain.model.DriveTimerGroup
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class HomeUiState(
    val isLoading: Boolean = false,
    val timerGroups: List<DriveTimerGroup> = emptyList(),
    val addedTimerEvent: StateEventWithContent<String> = consumed(),
    val startedTimerEvent: StateEventWithContent<String> = consumed(),

    )

