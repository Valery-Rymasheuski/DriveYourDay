package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.ui.components.EditEntityRow
import com.example.app.driveyourday.util.constants.getDummyTimers

@Composable
fun EditTimerListScreen(
    onEditButtonClick: (EntityId) -> Unit,
    viewModel: EditTimerListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    EditTimerListScreen(timers = uiState.timers,
        onEditClick = { onEditButtonClick(it) },
        onDeleteClick = { viewModel.delete(it) }
    )
}

@Composable
fun EditTimerListScreen(
    timers: List<DriveTimer>,
    onEditClick: (EntityId) -> Unit,
    onDeleteClick: (EntityId) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp)) {
        timers.forEach {
            EditEntityRow(
                entityId = it.id,
                label = it.label,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Preview
@Composable
fun EditTimerListScreenPreview() {
    EditTimerListScreen(timers = getDummyTimers(), {}, {})
}