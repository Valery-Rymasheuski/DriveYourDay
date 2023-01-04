package com.example.app.driveyourday.ui.screens.login

import android.util.Log
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(navController: NavHostController) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val loginNavId = backStackEntry?.destination?.route?.let { route ->
        LoginNavId.values().firstOrNull { it.name == route }
    }
    if (loginNavId != null) {
        Log.i(TAG, "backStackEntry loginNavId: ${loginNavId.name}")
        val loginNavGraph = remember(backStackEntry) {
            Log.i(TAG, "get loginNavGraph")
            navController.getBackStackEntry(ROUTE_LOGIN)
        }
        LoginScreen(loginNavGraph, navController)
    } else {
        Log.i(TAG, "Skip route: ${backStackEntry?.destination?.route}")
    }
}

@Composable
fun LoginScreen(
    loginNavGraph: NavBackStackEntry,
    navController: NavHostController
) {
    val loginViewModel = hiltViewModel<LoginViewModel>(loginNavGraph)
    //sync back flow
    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let { route ->
            LoginNavId.values().firstOrNull { it.name == route }?.let { it ->
                loginViewModel.onDestinationChanged(it)
            }
        }
    }
}