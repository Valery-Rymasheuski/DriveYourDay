package com.example.app.driveyourday.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app.driveyourday.R
import com.example.app.driveyourday.ui.components.AddFab
import com.example.app.driveyourday.ui.navigation.DriveDestinations
import com.example.app.driveyourday.ui.navigation.DriveNavGraph
import com.example.app.driveyourday.ui.screens.login.ROUTE_LOGIN

private const val TAG = "DriveYourDayApp"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveYourDayApp(navController: NavHostController = rememberNavController()) {


    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        backStackEntry?.destination?.route?.let { route ->
            Log.i(TAG, "current route: $route")
            val routeWithoutParams = route.substringBefore('?')
            DriveDestinations.values()
                .firstOrNull { it.name == routeWithoutParams } //TODO check LoginRoutes
        }
            ?: DriveDestinations.HOME
    val showEditTimersButton = currentRoute == DriveDestinations.HOME
    val showMenuButton = currentRoute == DriveDestinations.HOME
    val showLoginButton = currentRoute == DriveDestinations.HOME
    val canNavigateUp = navController.previousBackStackEntry != null

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            DriveTopAppBar(
                currentRoute.titleResId,
                onEditTimersClick = { navController.navigate(DriveDestinations.EDIT_TIMERS.name) },
                onLoginClick = { navController.navigate(ROUTE_LOGIN) },
                { navController.navigateUp() }, //TODO
                canNavigateUp,
                showEditTimersButton = showEditTimersButton,
                showLoginButton,
                showMenuButton,
            )
        }, floatingActionButton = {
            val fabClickDestination = when (currentRoute) {
                DriveDestinations.EDIT_TIMERS -> DriveDestinations.ADD_TIMER
                DriveDestinations.EDIT_TIMER_GROUPS -> DriveDestinations.ADD_TIMER_GROUP
                else -> null
            }
            if (fabClickDestination != null) {
                AddFab {
                    navController.navigate(fabClickDestination.name)
                }
            }
        }, floatingActionButtonPosition = FabPosition.End
    ) {
        Box(modifier = Modifier.padding(it)) {
            DriveNavGraph(
                navController,
                snackbarHostState = snackbarHostState,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveTopAppBar(
    @StringRes titleResId: Int,
    onEditTimersClick: () -> Unit,
    onLoginClick: () -> Unit,
    navigateUp: () -> Unit,
    canNavigateUp: Boolean,
    showEditTimersButton: Boolean,
    showLoginButton: Boolean,
    showMenuButton: Boolean,
) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = titleResId),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    },
        navigationIcon = {
            if (showMenuButton) {
                IconButton(onClick = { /*TODO*/ }) { //TODO
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "null")
                }
            } else {
                if (canNavigateUp) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.screen_back)
                        )
                    }
                }
            }
        },
        actions = {
            if (showLoginButton) {
                IconButton(onClick = onLoginClick) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = stringResource(id = R.string.btn_login)
                    )
                }
            }
            if (showEditTimersButton) {
                IconButton(onClick = onEditTimersClick) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.btn_edit)
                    )
                }
            }
        }
    )
}