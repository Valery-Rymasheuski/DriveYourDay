package com.example.app.driveyourday.ui.screens

import com.example.app.driveyourday.data.util.EntityId

data class AddTimerGroupUiState(
    val name: String = "",
    val editedId: EntityId? = null,
) {

    fun isFieldsValid() = name.isNotBlank()
}