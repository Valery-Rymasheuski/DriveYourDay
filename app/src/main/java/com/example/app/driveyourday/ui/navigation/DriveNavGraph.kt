package com.example.app.driveyourday.ui.navigation

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app.driveyourday.ui.screens.*
import com.example.app.driveyourday.ui.screens.login.LoginScreen
import com.example.app.driveyourday.ui.screens.login.loginGraph

const val TAG = "DriveNavGraph"
const val ARG_TIMER_ID = "timerId"
const val ARG_TIMER_GROUP_ID = "timerGroupId"

fun NavHostController.navigate(route: DriveDestinations) = navigate(route.name)

@Composable
fun DriveNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: DriveDestinations = DriveDestinations.HOME,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Log.i(TAG, "init navGraph")
    NavHost(
        navController = navController,
        startDestination = startDestination.name,
        modifier = modifier,
    ) {
        composable(DriveDestinations.HOME.name) {
            HomeRoute(snackbarHostState = snackbarHostState)
        }
        composable(DriveDestinations.SETTINGS.name) {
            SettingsScreen()
        }
        composable(DriveDestinations.ABOUT.name) {
            AboutScreen()
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
        composable(DriveDestinations.EDIT_TIMER_GROUPS.name) {
            EditTimerGroupsScreen(onEditClick = { id -> navController.navigate("${DriveDestinations.ADD_TIMER_GROUP.name}?$ARG_TIMER_GROUP_ID=$id") })
        }
        composable(
            route = "${DriveDestinations.ADD_TIMER_GROUP.name}?$ARG_TIMER_GROUP_ID={$ARG_TIMER_GROUP_ID}",
            arguments = listOf(navArgument(ARG_TIMER_GROUP_ID) {
                nullable = true
            })
        ) {
            AddTimerGroupScreen(onCancelClick = { navController.navigateUp() },
                onSuccessAdd = { navController.navigateUp() })
        }
        composable(DriveDestinations.COUNTDOWNS.name) {
            CountdownsScreen()
        }

        loginGraph(navController)
    }
    LoginScreen(navController = navController)
}