package com.example.app.driveyourday.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.app.driveyourday.ui.navigation.NavigationManager
import com.example.app.driveyourday.ui.navigation.TAG
import com.example.app.driveyourday.ui.theme.DriveYourDayTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DriveYourDayTheme {
                val navController = rememberNavController()
                navigationManager.commands.collectAsStateWithLifecycle().value.also { command ->
                    if (command.isNotEmpty()) {
                        if (command.skipSameRoute) {
                            val backStackEntry = navController.currentBackStackEntry
                            if (command.destination == backStackEntry?.destination?.route) {
                                Log.i(TAG, "skip same route ${command.destination}")
                                return@also
                            }
                        }
                        Log.i(TAG, "navigate to: ${command.destination}")
                        navController.navigate(command.destination) {
                            launchSingleTop = command.launchSingleTop
                            command.popUpTo?.let {
                                popUpTo(it) {
                                    inclusive = command.inclusive
                                }
                            }
                        }
                    }
                }
                DriveYourDayApp(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DriveYourDayApp()
}