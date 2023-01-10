package com.example.app.driveyourday.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.app.driveyourday.R

@Composable
fun AboutScreen() {
    Text(text = stringResource(id = R.string.screen_about))
}