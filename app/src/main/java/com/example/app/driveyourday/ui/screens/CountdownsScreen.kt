package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.app.driveyourday.R
import com.example.app.driveyourday.domain.model.Countdown
import com.example.app.driveyourday.domain.model.getLeftMinutes
import com.example.app.driveyourday.util.getCurrentMillis
import com.example.app.driveyourday.util.toMillisFromMinutes

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CountdownsScreen(viewModel: CountdownViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CountdownScreen(uiState = uiState)
}

@Composable
fun CountdownScreen(uiState: CountdownsUiState) {
    if (!uiState.isLoading) {
        val verticalScrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(verticalScrollState)) {
            uiState.countdowns.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = item.countdown.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(
                                id = R.string.countdown_left_minutes,
                                item.leftMinutes
                            )
                        )
                    }
                }

            }
        }
    } else {
        //TODO
    }
}

@Preview
@Composable
fun CountdownScreenPreview() {
    val currentMillis = getCurrentMillis()
    val items = listOf(
        Countdown(
            1,
            2,
            33L.toMillisFromMinutes(),
            "Cooking time",
            startedTimeMillis = currentMillis - 20L.toMillisFromMinutes()
        ),
        Countdown(
            2,
            3,
            21L.toMillisFromMinutes(),
            "Working time",
            startedTimeMillis = currentMillis - 10L.toMillisFromMinutes()
        ),
    )
    CountdownScreen(
        uiState = CountdownsUiState(
            isLoading = false,
            items.map { CountdownAtTimeInfo(it, it.getLeftMinutes(currentMillis)) }
        ))
}