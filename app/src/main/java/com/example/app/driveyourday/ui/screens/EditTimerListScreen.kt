package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.driveyourday.R
import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.domain.model.DriveTimer
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
            EditTimerRow(
                timer = it,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
            )
        }
    }
}

@Composable
fun EditTimerRow(
    timer: DriveTimer,
    onEditClick: (EntityId) -> Unit,
    onDeleteClick: (EntityId) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = timer.label,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1F)
        )
        Button(
            onClick = { onEditClick(timer.id) },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(R.string.btn_edit)
            )
        }
        Button(onClick = { onDeleteClick(timer.id) }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.btn_delete)
            )
        }
    }
}

@Preview
@Composable
fun EditTimerListScreenPreview() {
    EditTimerListScreen(timers = getDummyTimers(), {}, {})
}