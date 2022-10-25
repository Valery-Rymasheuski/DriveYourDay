package com.example.app.driveyourday.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.driveyourday.ui.screens.*

fun NavHostController.navigate(route: DriveDestinations) = navigate(route.name)

@Composable
fun DriveNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: DriveDestinations = DriveDestinations.HOME,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.name
    ) {
        composable(DriveDestinations.HOME.name) {
            HomeRoute()
        }
        composable(DriveDestinations.SETTINGS.name) {
            SettingsRoute()
        }
        composable(DriveDestinations.EDIT_TIMERS.name) {
            EditTimerListRoute()
        }
        composable(DriveDestinations.ADD_TIMER.name) {
            AddTimerScreen(onCancelButtonClick = { navController.navigateUp() },
                onSaveSuccessNavigate = { navController.navigate(DriveDestinations.HOME) })
        }
    }
}