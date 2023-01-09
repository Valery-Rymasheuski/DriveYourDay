package com.example.app.driveyourday.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.app.driveyourday.R

@Composable
fun AddFab(onClickAction: () -> Unit) {
    FloatingActionButton(onClick = onClickAction) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.btn_add)
        )
    }
}