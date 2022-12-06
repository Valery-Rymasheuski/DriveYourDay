package com.example.app.driveyourday.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavHostController) {
    //sync back flow
    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let { route ->
            loginViewModel.onDestinationChanged(LoginNavId.valueOf(route))
        }
    }

    LoginStep(loginViewModel, navController)
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginStep(loginViewModel: LoginViewModel, navController: NavHostController) {
    val stepFlow by loginViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = Outcome.Success(
            LoginStep.Start
        )
    )
    when (stepFlow.data) {
        is LoginStep.Start -> {
            //TODO do nothing?
        }
        is LoginStep.EnterOptionsStep -> {
            navigateToOptionsStep(navController)
        }
        is LoginStep.EnterEmailStep -> {
            navigateToEmailStep(navController)
        }
        is LoginStep.EnterPasswordStep -> {
            navigateToPasswordStep(navController)
        }
        LoginStep.End -> {
            navigateToEndStep(navController)
        }
    }
}

fun navigateToOptionsStep(navController: NavHostController) {
    navController.navigate(LoginNavId.OPTIONS.name) {
        launchSingleTop = true
        popUpTo(LoginNavId.START.name) {
            inclusive = true
        }
    }
}

fun navigateToEmailStep(navController: NavHostController) {
    navController.navigate(LoginNavId.EMAIL.name) {
        launchSingleTop = true
    }
}

fun navigateToPasswordStep(navController: NavHostController) {
    navController.navigate(LoginNavId.PASSWORD.name) {
        launchSingleTop = true
    }
}

fun navigateToEndStep(navController: NavHostController) {
    navController.navigate(LoginNavId.END.name) {
        popUpTo(LoginNavId.OPTIONS.name) {
            inclusive = true
        }
        launchSingleTop = true
    }
}