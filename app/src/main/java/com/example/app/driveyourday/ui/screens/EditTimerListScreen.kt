package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.R
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.util.constants.getDummyTimers

@Composable
fun EditTimerListScreen(uiState: EditTimerListUiState) {
    EditTimerListScreen(timers = uiState.timers)
}

@Composable
fun EditTimerListScreen(timers: List<DriveTimer>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        timers.forEach {
            EditTimerRow(timer = it)
        }
    }

}

@Composable
fun EditTimerRow(timer: DriveTimer, modifier: Modifier = Modifier) {
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
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(R.string.btn_edit)
            )
        }
        Button(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.btn_delete)
            )
        }
    }
}

@Preview
@Composable
fun EditTimerListScreen() {
    EditTimerListScreen(timers = getDummyTimers())
}