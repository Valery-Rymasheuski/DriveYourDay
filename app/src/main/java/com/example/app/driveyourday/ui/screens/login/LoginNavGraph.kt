package com.example.app.driveyourday.ui.screens.login

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.app.driveyourday.R
import com.example.app.driveyourday.ui.screens.login.steps.EnterEmailScreen
import com.example.app.driveyourday.ui.screens.login.steps.EnterPasswordScreen
import com.example.app.driveyourday.ui.screens.login.steps.LoginOptionsScreen

private const val TAG = "loginGraph"
const val ROUTE_LOGIN = "login"

@OptIn(ExperimentalLifecycleComposeApi::class)
fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    Log.i(TAG, "init $TAG")
    navigation(LoginNavId.LOGIN_OPTIONS.name, ROUTE_LOGIN) {
        composable(LoginNavId.LOGIN_OPTIONS.name) { backStackEntry ->
            Log.i(TAG, "composable Options")
            val loginViewModel = getLoginViewModel(backStackEntry, navController)
            val uiState by loginViewModel.enterOptionsStep.collectAsStateWithLifecycle()
            LoginOptionsScreen(
                uiState = uiState,
                onOptionsSelected = loginViewModel::onOptionSelected
            )
        }
        composable(LoginNavId.LOGIN_EMAIL.name) { backStackEntry ->
            Log.i(TAG, "composable Email")
            val loginViewModel = getLoginViewModel(backStackEntry, navController)
            val uiState by loginViewModel.enterEmailStep.collectAsStateWithLifecycle()
            EnterEmailScreen(
                uiState = uiState,
                onEmailEntered = loginViewModel::onEmailEntered
            )
        }
        composable(LoginNavId.LOGIN_PASSWORD.name) { backStackEntry ->
            Log.i(TAG, "composable password")
            val loginViewModel = getLoginViewModel(backStackEntry, navController)
            val uiState by loginViewModel.enterPasswordStep.collectAsStateWithLifecycle()

            EnterPasswordScreen(
                uiState = uiState,
                onPasswordEntered = loginViewModel::onPasswordEntered
            )
        }
        composable(LoginNavId.LOGIN_END.name) {
            Log.i(TAG, "composable login_end")
            Text(text = stringResource(R.string.logged_in_success))
        }
    }
}

@Composable
private fun getLoginViewModel(
    backStackEntry: NavBackStackEntry,
    navController: NavHostController
): LoginViewModel {
    val loginNavGraph = remember(backStackEntry) {
        navController.getBackStackEntry(ROUTE_LOGIN)
    }
    return hiltViewModel(loginNavGraph)
}