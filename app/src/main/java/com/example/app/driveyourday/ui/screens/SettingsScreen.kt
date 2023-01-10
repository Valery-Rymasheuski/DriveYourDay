package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.driveyourday.R
import com.example.app.driveyourday.domain.model.UserPreferences

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    SettingsScreen(uiState, bottomNavigationChanged = viewModel::bottomNavigationChanged)
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    bottomNavigationChanged: (Boolean) -> Unit
) {
    if (!uiState.isLoading) {
        requireNotNull(uiState.userPreferences)
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = R.string.screen_settings),
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.settings_bottom_navigation),
                    modifier = Modifier.weight(1F)
                )
                Switch(
                    checked = uiState.userPreferences.bottomNavigation,
                    onCheckedChange = bottomNavigationChanged,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    } else {
        Text(text = stringResource(id = R.string.loading))
    }

}

@Preview()
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(SettingsUiState(
        isLoading = false,
        UserPreferences(bottomNavigation = true),
    ),
        bottomNavigationChanged = {})
}