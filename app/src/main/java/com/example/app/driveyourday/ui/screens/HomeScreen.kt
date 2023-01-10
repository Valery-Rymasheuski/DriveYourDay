package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.model.DriveTimerGroup
import com.example.app.driveyourday.util.constants.getDummyTimerGroups
import com.example.app.driveyourday.R

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
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(groups, key = { it.id }) {
            TimerCard(timerGroup = it)
        }
    }
}

@Composable
fun TimerCard(timerGroup: DriveTimerGroup, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = timerGroup.name,
                style = MaterialTheme.typography.headlineSmall
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
        colors = ButtonDefaults.buttonColors(containerColor = timer.color)
    ) {
        //TODO add icon
        Text(text = stringResource(id = R.string.timer_label_full, timer.label, timer.minutes))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    TimerCards(getDummyTimerGroups())
}