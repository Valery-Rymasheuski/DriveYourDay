package com.example.app.driveyourday.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app.driveyourday.ui.screens.*

const val ARG_TIMER_ID = "timerId"

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
            EditTimerListScreen(
                onEditButtonClick =
                { id -> navController.navigate("${DriveDestinations.ADD_TIMER.name}?$ARG_TIMER_ID=$id") })
        }
        composable(
            "${DriveDestinations.ADD_TIMER.name}?$ARG_TIMER_ID={$ARG_TIMER_ID}",
            arguments = listOf(navArgument(ARG_TIMER_ID) {
                nullable = true
            })
        ) {
            AddTimerScreen(
                onCancelButtonClick = { navController.navigateUp() },
                onNavigateToHome = {
                    navController.navigate(
                        DriveDestinations.HOME.name,
                        NavOptions.Builder().setPopUpTo(DriveDestinations.HOME.name, true).build()
                    )
                },
                onNavigateToEditTimerList = { navController.navigate(DriveDestinations.EDIT_TIMERS) }
            )
        }
    }
}