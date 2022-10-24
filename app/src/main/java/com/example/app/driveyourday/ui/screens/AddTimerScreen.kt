package com.example.app.driveyourday.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.R
import androidx.compose.material3.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddTimerScreen(uiState: AddTimerUiState) {

    var label: String by rememberSaveable {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedTimerGroupName by remember {
        mutableStateOf("")
    }

    val options = listOf("group1", "group2")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = label,
            placeholder = { Text(text = stringResource(id = R.string.enter_timer_name)) },
            onValueChange = { v -> label = v },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = selectedTimerGroupName,
                onValueChange = {},
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                label = { Text("Label") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                options.forEach{option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedTimerGroupName = option
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddTimerScreenPreview() {
    AddTimerScreen(uiState = AddTimerUiState("test"))
}