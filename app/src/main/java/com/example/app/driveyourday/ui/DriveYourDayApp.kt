package com.example.app.driveyourday.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app.driveyourday.R
import com.example.app.driveyourday.ui.components.AddFab
import com.example.app.driveyourday.ui.navigation.DriveDestinations
import com.example.app.driveyourday.ui.navigation.DriveNavGraph
import com.example.app.driveyourday.ui.navigation.navigate
import com.example.app.driveyourday.ui.screens.login.ROUTE_LOGIN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "DriveYourDayApp"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveYourDayApp(
    navController: NavHostController = rememberNavController(),
    bottomNavigation: Boolean
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        backStackEntry?.destination?.route?.let { route ->
            Log.i(TAG, "current route: $route")
            val routeWithoutParams = route.substringBefore('?')
            DriveDestinations.values()
                .firstOrNull { it.name == routeWithoutParams } //TODO check LoginRoutes
        }
            ?: DriveDestinations.HOME

    if (bottomNavigation) {
        DriveScaffold(
            navController = navController,
            currentRoute = currentRoute,
            menuButtonClick = { },
            showNavigationBar = true,
            showMenuButton = false,
        )
    } else {
        val scope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet() {
                    Spacer(Modifier.height(12.dp))
                    DriveDrawerItem(
                        labelTextId = R.string.drawer_add_timer,
                        iconVector = Icons.Default.Add,
                        currentRoute = currentRoute,
                        destinationRoute = DriveDestinations.ADD_TIMER,
                        navController = navController,
                        scope = scope,
                        drawerState = drawerState
                    )
                    DriveDrawerItem(
                        labelTextId = R.string.drawer_edit_timers,
                        iconVector = Icons.Default.Edit,
                        currentRoute = currentRoute,
                        destinationRoute = DriveDestinations.EDIT_TIMERS,
                        navController = navController,
                        scope = scope,
                        drawerState = drawerState
                    )
                    DriveDrawerItem(
                        labelTextId = R.string.drawer_add_timer_group,
                        iconVector = Icons.Default.Add,
                        currentRoute = currentRoute,
                        destinationRoute = DriveDestinations.ADD_TIMER_GROUP,
                        navController = navController,
                        scope = scope,
                        drawerState = drawerState
                    )
                    DriveDrawerItem(
                        labelTextId = R.string.drawer_edit_timer_groups,
                        iconVector = Icons.Default.Edit,
                        currentRoute = currentRoute,
                        destinationRoute = DriveDestinations.EDIT_TIMER_GROUPS,
                        navController = navController,
                        scope = scope,
                        drawerState = drawerState
                    )
                    DriveDrawerItem(
                        labelTextId = R.string.drawer_settings,
                        iconVector = Icons.Default.Settings,
                        currentRoute = currentRoute,
                        destinationRoute = DriveDestinations.SETTINGS,
                        navController = navController,
                        scope = scope,
                        drawerState = drawerState
                    )
                    DriveDrawerItem(
                        labelTextId = R.string.drawer_about,
                        iconVector = Icons.Default.Face,
                        currentRoute = currentRoute,
                        destinationRoute = DriveDestinations.ABOUT,
                        navController = navController,
                        scope = scope,
                        drawerState = drawerState
                    )
                }
            }) {
            DriveScaffold(navController = navController,
                currentRoute = currentRoute,
                menuButtonClick = { scope.launch { drawerState.open() } }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveScaffold(
    navController: NavHostController,
    currentRoute: DriveDestinations,
    menuButtonClick: () -> Unit,
    showNavigationBar: Boolean = false,
    showMenuButton: Boolean = currentRoute == DriveDestinations.HOME,
) {
    val showEditTimersButton = currentRoute == DriveDestinations.HOME
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
                menuButtonClick = menuButtonClick,
                canNavigateUp = canNavigateUp,
                showEditTimersButton = showEditTimersButton,
                showLoginButton = showLoginButton,
                showMenuButton = showMenuButton,
            )
        }, bottomBar = {
            if (showNavigationBar) {
                DriveNavigationBar(
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        },
        floatingActionButton = {
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

@Composable
fun DriveNavigationBar(
    currentRoute: DriveDestinations,
    navController: NavHostController,
) {
    NavigationBar() {
        DriveNavBarItem(
            labelTextId = R.string.nav_bar_timers,
            icon = Icons.Default.Edit,
            navController = navController,
            currentRoute = currentRoute,
            destinationRoute = DriveDestinations.EDIT_TIMERS
        )
        DriveNavBarItem(
            labelTextId = R.string.nav_bar_groups,
            icon = Icons.Default.Edit,
            navController = navController,
            currentRoute = currentRoute,
            destinationRoute = DriveDestinations.EDIT_TIMER_GROUPS
        )
        DriveNavBarItem(
            labelTextId = R.string.drawer_settings,
            icon = Icons.Default.Settings,
            navController = navController,
            currentRoute = currentRoute,
            destinationRoute = DriveDestinations.SETTINGS
        )
        DriveNavBarItem(
            labelTextId = R.string.drawer_about,
            icon = Icons.Default.Face,
            navController = navController,
            currentRoute = currentRoute,
            destinationRoute = DriveDestinations.ABOUT
        )
    }
}

@Composable
fun RowScope.DriveNavBarItem(
    @StringRes labelTextId: Int,
    icon: ImageVector,
    navController: NavHostController,
    currentRoute: DriveDestinations,
    destinationRoute: DriveDestinations,
) {
    NavigationBarItem(
        label = { Text(text = stringResource(id = labelTextId)) },
        icon = { Icon(imageVector = icon, contentDescription = null) },
        selected = currentRoute == destinationRoute,
        onClick = { navController.navigate(destinationRoute) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveDrawerItem(
    @StringRes labelTextId: Int,
    iconVector: ImageVector,
    currentRoute: DriveDestinations,
    destinationRoute: DriveDestinations,
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    NavigationDrawerItem(label = { Text(text = stringResource(id = labelTextId)) },
        icon = {
            Icon(
                imageVector = iconVector,
                contentDescription = null
            )
        },
        selected = currentRoute == destinationRoute,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        onClick = {
            scope.launch {
                drawerState.close() //TODO is it necessary?
            }
            navController.navigate(destinationRoute.name)
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveTopAppBar(
    @StringRes titleResId: Int,
    onEditTimersClick: () -> Unit,
    onLoginClick: () -> Unit,
    navigateUp: () -> Unit,
    menuButtonClick: () -> Unit,
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
                IconButton(onClick = menuButtonClick) {
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