package com.example.app.driveyourday.ui.screens.login.steps

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.ui.screens.login.CommonLoginScreen
import com.example.app.driveyourday.ui.screens.login.LoginStep
import com.example.app.driveyourday.ui.screens.login.Outcome
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val TAG = "LoginOptionsScreen"

@Composable
fun LoginOptionsScreen(
    uiState: Outcome<LoginStep.EnterOptionsStep>,
    onOptionsSelected: (String) -> Unit
) {
    Log.i(TAG, "$TAG uiState: $uiState")
    LoginOptionsScreenContent(options = uiState.data.options, onOptionsSelected = onOptionsSelected)
}

@Composable
fun LoginOptionsScreenContent(options: ImmutableList<String>, onOptionsSelected: (String) -> Unit) {
    Log.i(TAG, "loginOptionsScreenContent options=${options.size}")
    CommonLoginScreen(title = "Login options") {
        options.forEach { option ->
            OutlinedButton(onClick = { onOptionsSelected(option) }) {
                Text(
                    text = option,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Divider()
        }
    }
}

@Preview
@Composable
fun LoginOptionsScreenPreview() {
    LoginOptionsScreenContent(options = persistentListOf("Option1", "Option2", "Option3"),
        onOptionsSelected = {})
}