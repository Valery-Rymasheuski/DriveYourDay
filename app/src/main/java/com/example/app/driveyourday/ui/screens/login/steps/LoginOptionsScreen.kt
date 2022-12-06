package com.example.app.driveyourday.ui.screens.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginOptionsScreen(
    dataFlow: Flow<Outcome<LoginStep.EnterOptionsStep>>,
    onOptionsSelected: (String) -> Unit
) {
    val uiState by dataFlow.collectAsStateWithLifecycle(
        initialValue = Outcome.Success(
            LoginStep.EnterOptionsStep(
                emptyList()
            )
        )
    )

    LoginOptionsScreenContent(options = uiState.data.options, onOptionsSelected = onOptionsSelected)
}

@Composable
fun LoginOptionsScreenContent(options: List<String>, onOptionsSelected: (String) -> Unit) {
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
    LoginOptionsScreenContent(options = listOf("Option1", "Option2", "Option3"),
        onOptionsSelected = {})
}