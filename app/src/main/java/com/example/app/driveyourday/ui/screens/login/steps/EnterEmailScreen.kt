package com.example.app.driveyourday.ui.screens.login.steps

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.ui.screens.login.*

private const val TAG = "EnterEmailScreen"

@Composable
fun EnterEmailScreen(
    uiState: Outcome<LoginStep.EnterEmailStep>,
    onEmailEntered: (String) -> Unit
) {
    Log.i(TAG, "$TAG uiState: $uiState")
    EnterEmailScreenContent(uiState = uiState, onEmailEntered = onEmailEntered)
}

@Composable
fun EnterEmailScreenContent(
    uiState: Outcome<LoginStep.EnterEmailStep>,
    onEmailEntered: (String) -> Unit
) {
    Log.i(TAG, "EnterEmailScreenContent")
    var text by rememberSaveable {
        mutableStateOf(uiState.data.email.orEmpty())
    }
    CommonLoginScreen(title = "Enter email") {
        TextField(value = text,
            onValueChange = { text = it },
            label = {
                Text(text = "Email:")
            })

        when (uiState) {
            is Outcome.Loading -> {
                Text(text = "loading..")
            }
            is Outcome.Error -> {
                ErrorLabel(text = uiState.ex.toString())
            }
            is Outcome.Success -> {
                //do nothing
            }
        }
        Button(
            onClick = { onEmailEntered(text) },
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview
@Composable
fun EnterEmailPreview() {
    EnterEmailScreenContent(
        uiState = Outcome.Error(
            LoginStep.EnterEmailStep(null),
            InvalidEmilException()
        ),
        onEmailEntered = {})
}