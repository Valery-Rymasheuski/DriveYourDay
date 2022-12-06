package com.example.app.driveyourday.ui.screens.login.steps

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.app.driveyourday.ui.screens.login.*
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EnterEmailScreen(
    dataFlow: Flow<Outcome<LoginStep.EnterEmailStep>>,
    onEmailEntered: (String) -> Unit
) {
    val uiState by dataFlow.collectAsStateWithLifecycle(
        Outcome.Success(
            LoginStep.EnterEmailStep(
                null
            )
        )
    )
    EnterEmailScreenContent(uiState = uiState, onEmailEntered = onEmailEntered)
}

@Composable
fun EnterEmailScreenContent(
    uiState: Outcome<LoginStep.EnterEmailStep>,
    onEmailEntered: (String) -> Unit
) {
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