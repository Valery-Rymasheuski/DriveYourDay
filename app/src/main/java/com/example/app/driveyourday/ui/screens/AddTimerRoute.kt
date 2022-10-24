package com.example.app.driveyourday.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddTimerRoute(viewModel: AddTimerViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    AddTimerScreen(uiState = uiState)
}