package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import com.example.app.driveyourday.util.NamedColor

data class AddTimerUiState(
    val groups: List<DriveTimerGroupSimple> = emptyList(),
    val colors: List<NamedColor> = emptyList(),
    val editedId: EntityId? = null,
    val timerName: String = "",
    val selectedTimerGroup: DriveTimerGroupSimple? = null,
    val selectedColor: NamedColor? = null
) {
    fun isFieldsValid() = timerName.isNotBlank() && selectedTimerGroup != null
            && selectedColor != null
}