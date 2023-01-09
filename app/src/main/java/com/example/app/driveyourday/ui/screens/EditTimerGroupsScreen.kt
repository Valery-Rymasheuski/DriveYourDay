package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import com.example.app.driveyourday.ui.components.EditEntityRow

@Composable
fun EditTimerGroupsScreen(
    onEditClick: (EntityId) -> Unit,
    viewModel: EditTimerGroupsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    EditTimerGroupsScreen(groups = uiState.groups,
        onEditClick = onEditClick,
        onDeleteClick = { viewModel.delete(it) })
}

@Composable
fun EditTimerGroupsScreen(
    groups: List<DriveTimerGroupSimple>,
    onEditClick: (EntityId) -> Unit,
    onDeleteClick: (EntityId) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp)) {
        groups.forEach {
            EditEntityRow(
                entityId = it.id,
                label = it.name,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Preview
@Composable
fun EditTimerGroupsPreview() {
    EditTimerGroupsScreen(groups = listOf(
        DriveTimerGroupSimple(1, "Group1", 1),
        DriveTimerGroupSimple(2, "Group2", 2)
    ),
        onEditClick = {},
        onDeleteClick = {})
}