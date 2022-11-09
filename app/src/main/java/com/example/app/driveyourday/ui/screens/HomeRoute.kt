package com.example.app.driveyourday.ui.screens

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.example.app.driveyourday.R
import de.palm.composestateevents.EventEffect

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    EventEffect(event = uiState.addedTimerEvent,
        onConsumed = { viewModel.setAddedTimerEventConsumed() },
        action = { timerName ->
            scaffoldState.snackbarHostState.showSnackbar(
                context.resources.getString(
                    R.string.msg_added_timer,
                    timerName
                )
            )
        })
    HomeScreen(uiState) //TODO remove HomeRoute ?
}