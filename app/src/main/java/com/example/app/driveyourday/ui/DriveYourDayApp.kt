package com.example.app.driveyourday.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.app.driveyourday.ui.navigation.DriveNavGraph
import com.example.app.driveyourday.ui.theme.DriveYourDayTheme

@Composable
fun DriveYourDayApp() {
    DriveYourDayTheme {
        val navController = rememberNavController()
        DriveNavGraph(navController)
    }
}