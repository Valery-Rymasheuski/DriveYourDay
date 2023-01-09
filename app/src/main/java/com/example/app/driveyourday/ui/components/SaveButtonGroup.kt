package com.example.app.driveyourday.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.app.driveyourday.R

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