package com.example.app.driveyourday.ui.screens.login

import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.app.driveyourday.R
import com.example.app.driveyourday.ui.screens.login.steps.EnterEmailScreen
import com.example.app.driveyourday.ui.screens.login.steps.EnterPasswordScreen
import kotlinx.coroutines.flow.Flow

const val ROUTE_LOGIN = "login"

fun NavGraphBuilder.loginGraph(navController: NavHostController) {


    navigation(LoginNavId.START.name, ROUTE_LOGIN) {
        composable(LoginNavId.START.name) {
            Text(text = "EMPTY") //TODO
        }
        composable(LoginNavId.OPTIONS.name) { backStackEntry ->

            val loginNavGraph = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN)
            }
            val loginViewModel = hiltViewModel<LoginViewModel>(loginNavGraph)
            val dataFlow =
                loginViewModel.uiState as Flow<Outcome<LoginStep.EnterOptionsStep>> //TODO

            LoginOptionsScreen(
                dataFlow = dataFlow,
                onOptionsSelected = loginViewModel::onOptionSelected
            )
        }
        composable(LoginNavId.EMAIL.name) { backStackEntry ->
            val loginNavGraph = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN)
            }
            val loginViewModel = hiltViewModel<LoginViewModel>(loginNavGraph)
            val dataFlow = loginViewModel.uiState as Flow<Outcome<LoginStep.EnterEmailStep>>
            EnterEmailScreen(dataFlow = dataFlow, onEmailEntered = loginViewModel::onEmailEntered)
        }
        composable(LoginNavId.PASSWORD.name) { backStackEntry ->
            val loginNavGraph = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN)
            }
            val loginViewModel = hiltViewModel<LoginViewModel>(loginNavGraph)
            val dataFlow = loginViewModel.uiState as Flow<Outcome<LoginStep.EnterPasswordStep>>

            EnterPasswordScreen(
                dataFlow = dataFlow,
                onPasswordEntered = loginViewModel::onPasswordEntered
            )
        }
        composable(LoginNavId.END.name) {
            Text(text = stringResource(R.string.logged_in_success))
        }
    }
}