package com.example.app.driveyourday.ui.screens.login.steps

import androidx.compose.foundation.layout.Column
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
import com.example.app.driveyourday.ui.screens.login.CommonLoginScreen
import com.example.app.driveyourday.ui.screens.login.ErrorLabel
import com.example.app.driveyourday.ui.screens.login.LoginStep
import com.example.app.driveyourday.ui.screens.login.Outcome
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EnterPasswordScreen(
    dataFlow: Flow<Outcome<LoginStep.EnterPasswordStep>>,
    onPasswordEntered: (String) -> Unit
) {
    val uiState by dataFlow.collectAsStateWithLifecycle(initialValue = Outcome.Success(LoginStep.EnterPasswordStep()))
    EnterPasswordScreenContent(uiState = uiState, onPasswordEntered = onPasswordEntered)
}

@Composable
fun EnterPasswordScreenContent(
    uiState: Outcome<LoginStep.EnterPasswordStep>,
    onPasswordEntered: (String) -> Unit
) {
    CommonLoginScreen(title = "Enter password") {
        Column {
            var text by rememberSaveable {
                mutableStateOf("")
            }
            TextField(value = text,
                onValueChange = { text = it })

            if (uiState is Outcome.Error) {
                ErrorLabel(text = uiState.ex.toString())
            }
            ActionButton(isLoading = uiState is Outcome.Loading,
                onClick = { onPasswordEntered(text) })
        }
    }
}

@Composable
fun ActionButton(
    isLoading: Boolean, onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(vertical = 32.dp)
    ) {
        Text(text = if (isLoading) "Loading.." else "Continue")
    }
}

@Preview
@Composable
fun EnterPasswordPreview() {
    EnterPasswordScreenContent(uiState = Outcome.Loading(LoginStep.EnterPasswordStep()),
        onPasswordEntered = {})
}