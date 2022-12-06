package com.example.app.driveyourday.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app.driveyourday.ui.screens.*
import com.example.app.driveyourday.ui.screens.login.loginGraph

const val ARG_TIMER_ID = "timerId"

fun NavHostController.navigate(route: DriveDestinations) = navigate(route.name)

@Composable
fun DriveNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: DriveDestinations = DriveDestinations.HOME,
    scaffoldState: ScaffoldState,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.name
    ) {
        composable(DriveDestinations.HOME.name) {
            HomeRoute(scaffoldState = scaffoldState)
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
        ) { backStackEntry ->

            val homeEntry = remember(backStackEntry) {
                navController.getBackStackEntry(DriveDestinations.HOME.name)
            }
            val homeViewModel = hiltViewModel<HomeViewModel>(homeEntry)

            AddTimerScreen(
                onCancelButtonClick = { navController.navigateUp() },
                onNavigateToHome = {
                    navController.popBackStack(DriveDestinations.HOME.name, false)
                    /*navController.navigate(
                        DriveDestinations.HOME.name,
                        NavOptions.Builder().setPopUpTo(DriveDestinations.HOME.name, true).build()
                    )*/
                },
                onNavigateToEditTimerList = { navController.navigate(DriveDestinations.EDIT_TIMERS) },
                onSuccessAdd = { homeViewModel.setAddedTimerEvent(it) },
            )
        }

        loginGraph(navController)
    }
}