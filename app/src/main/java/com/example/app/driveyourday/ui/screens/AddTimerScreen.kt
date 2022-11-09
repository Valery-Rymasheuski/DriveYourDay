package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.driveyourday.domain.model.DriveTimer
import com.example.app.driveyourday.domain.model.DriveTimerGroupSimple
import com.example.app.driveyourday.ui.components.CommonDropdown
import com.example.app.driveyourday.util.NamedColor
import com.example.app.driveyourday.util.constants.getDummyGroupsSimple

@Composable
fun AddTimerScreen(
    onCancelButtonClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToEditTimerList: () -> Unit,
    onSuccessAdd: (DriveTimer) -> Unit,
    viewModel: AddTimerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    AddTimerScreen(
        uiState = uiState,
        onTimerNameChange = { viewModel.updateTimerName(it) },
        onSelectTimerGroup = { viewModel.updateSelectedGroup(it) },
        onSelectColor = { viewModel.updateSelectedColor(it) },
        onSaveClick = {
            viewModel.save(
                onNavigateToHome,
                onNavigateToEditTimerList,
                onSuccessAdd,
            )
        },
        onCancelClick = onCancelButtonClick,
        saveButtonEnabled = uiState.isFieldsValid(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimerScreen(
    uiState: AddTimerUiState,
    onTimerNameChange: (String) -> Unit,
    onSelectTimerGroup: (DriveTimerGroupSimple) -> Unit,
    onSelectColor: (NamedColor) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    saveButtonEnabled: Boolean,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = uiState.timerName,
            placeholder = { Text(text = stringResource(id = R.string.enter_timer_name)) },
            onValueChange = onTimerNameChange,
            modifier = Modifier.fillMaxWidth()
        )
        CommonDropdown(
            selectedValue = uiState.selectedTimerGroup,
            labelResId = R.string.text_field_group,
            options = uiState.groups,
            getDisplayText = { it.name },
            onOptionSelect = { onSelectTimerGroup(it) }
        )
        CommonDropdown(
            selectedValue = uiState.selectedColor,
            labelResId = R.string.text_field_color,
            options = uiState.colors,
            getDisplayText = { stringResource(it.nameResId) },
            onOptionSelect = { onSelectColor(it) }
        )
        SaveButtonGroup(
            onCancelClick = onCancelClick,
            onSaveClick = onSaveClick,
            saveButtonEnabled = saveButtonEnabled,
        )
    }
}

@Composable
fun SaveButtonGroup(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    saveButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(modifier = Modifier.weight(1F), onClick = onCancelClick) {
            Text(text = stringResource(R.string.btn_cancel).uppercase())
        }
        Button(
            modifier = Modifier.weight(1F),
            onClick = onSaveClick,
            enabled = saveButtonEnabled
        ) {
            Text(text = stringResource(id = R.string.btn_save).uppercase())
        }
    }
}

@Preview
@Composable
fun AddTimerScreenPreview() {
    AddTimerScreen(uiState = AddTimerUiState(getDummyGroupsSimple()),
        {}, {}, {}, {}, {}, true
    )
}