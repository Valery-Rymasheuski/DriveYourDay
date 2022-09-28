package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.util.constants.dummyTimerGroups

@Composable
fun HomeScreen(uiState: HomeUiState) {
    HomeScreen(groups = uiState.timerGroups)
}

@Composable
fun HomeScreen(groups: List<DriveTimerGroup>) {
    TimerCards(groups = groups)
}

@Composable
fun TimerCards(groups: List<DriveTimerGroup>) {
    LazyColumn {
        items(groups, key = { it.id!! }) {//TODO remove !!
            TimerCard(timerGroup = it)
        }
    }
}

@Composable
fun TimerCard(timerGroup: DriveTimerGroup, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = timerGroup.name,
                style = MaterialTheme.typography.h5
            )
            timerGroup.timers.forEach {
                TimerButton(timer = it)
            }
        }
    }
}

@Composable
fun TimerButton(timer: DriveTimer, modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = timer.color)
    ) {
        //TODO add icon
        Text(text = timer.label)
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    TimerCards(dummyTimerGroups)
}