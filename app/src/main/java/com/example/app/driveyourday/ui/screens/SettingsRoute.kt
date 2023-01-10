package com.example.app.driveyourday.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.driveyourday.R

@Composable
fun SettingsRoute() {
    Text(text = stringResource(id = R.string.screen_settings))
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsRoute()
}