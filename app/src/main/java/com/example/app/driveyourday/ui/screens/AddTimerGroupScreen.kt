package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.driveyourday.R
import com.example.app.driveyourday.ui.components.SaveButtonGroup

@Composable
fun AddTimerGroupScreen(
    onCancelClick: () -> Unit,
    onSuccessAdd: () -> Unit,
    viewModel: AddTimerGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AddTimerGroupScreen(
        uiState = uiState,
        onGroupNameChange = { viewModel.updateGroupName(it) },
        saveButtonEnabled = uiState.isFieldsValid(),
        onSaveClick = { viewModel.save(onSuccessAdd) },
        onCancelClick = onCancelClick
    )
}

@Composable
fun AddTimerGroupScreen(
    uiState: AddTimerGroupUiState,
    onGroupNameChange: (String) -> Unit,
    saveButtonEnabled: Boolean,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = uiState.name,
            onValueChange = onGroupNameChange,
            placeholder = { Text(stringResource(id = R.string.enter_timer_name)) },
            modifier = Modifier.fillMaxWidth()
        )
        SaveButtonGroup(
            onCancelClick = onCancelClick,
            onSaveClick = onSaveClick,
            saveButtonEnabled = saveButtonEnabled,
        )
    }
}

@Preview
@Composable
fun AddTimerGroupPreview() {
    AddTimerGroupScreen(
        uiState = AddTimerGroupUiState(),
        onGroupNameChange = {},
        saveButtonEnabled = false,
        onSaveClick = { },
        onCancelClick = { })
}