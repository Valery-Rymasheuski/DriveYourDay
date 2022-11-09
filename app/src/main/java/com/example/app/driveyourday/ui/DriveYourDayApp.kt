package com.example.app.driveyourday.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app.driveyourday.R
import com.example.app.driveyourday.ui.navigation.DriveDestinations
import com.example.app.driveyourday.ui.navigation.DriveNavGraph
import com.example.app.driveyourday.ui.theme.DriveYourDayTheme

@Composable
fun DriveYourDayApp() {
    DriveYourDayTheme {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            backStackEntry?.destination?.route?.let { DriveDestinations.valueOf(it.substringBefore('?')) }
                ?: DriveDestinations.HOME
        val showEditTimersButton = currentRoute == DriveDestinations.HOME
        val showMenuButton = currentRoute == DriveDestinations.HOME
        val canNavigateUp = navController.previousBackStackEntry != null

        val scaffoldState: ScaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                DriveTopAppBar(
                    currentRoute.titleResId,
                    { navController.navigate(DriveDestinations.EDIT_TIMERS.name) },
                    { navController.navigateUp() }, //TODO
                    canNavigateUp,
                    showEditTimersButton,
                    showMenuButton,
                )
            }, floatingActionButton = {
                if (currentRoute == DriveDestinations.EDIT_TIMERS) {
                    FloatingActionButton(onClick = { navController.navigate(DriveDestinations.ADD_TIMER.name) }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.btn_add)
                        )
                    }
                }
            }, floatingActionButtonPosition = FabPosition.End
        ) {
            Box(modifier = Modifier.padding(it)) {
                DriveNavGraph(navController, scaffoldState = scaffoldState)
            }
        }
    }
}

@Composable
fun DriveTopAppBar(
    @StringRes titleResId: Int,
    onEditTimersClick: () -> Unit,
    navigateUp: () -> Unit,
    canNavigateUp: Boolean,
    showEditTimersButton: Boolean,
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