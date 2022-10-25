package com.example.app.driveyourday.util

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class NamedColor(
    val color: Color,
    @StringRes val nameResId: Int,
)