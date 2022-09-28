package com.example.app.driveyourday.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.rememberNavController
import com.example.app.driveyourday.R
import com.example.app.driveyourday.ui.navigation.DriveNavGraph
import com.example.app.driveyourday.ui.theme.DriveYourDayTheme

@Composable
fun DriveYourDayApp() {
    DriveYourDayTheme {
        val navController = rememberNavController()
        val title = stringResource(id = R.string.app_name)
        Scaffold(topBar = { DriveTopAppBar(title) }) {
            Box(modifier = Modifier.padding(it)) {
                DriveNavGraph(navController)
            }
        }
    }
}

@Composable
fun DriveTopAppBar(title: String) {
    TopAppBar(title = {
        Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
    },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) { //TODO
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "null")
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "null")
            }
        }
    )
}