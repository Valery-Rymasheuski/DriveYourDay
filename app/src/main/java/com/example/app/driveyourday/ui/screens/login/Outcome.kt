package com.example.app.driveyourday.ui.screens.login

import androidx.compose.runtime.Immutable

@Immutable
sealed class Outcome<T>(open val data: T) {
    @Immutable
    data class Loading<T>(override val data: T) : Outcome<T>(data)
    @Immutable
    data class Success<T>(override val data: T) : Outcome<T>(data)
    @Immutable
    data class Error<T>(override val data: T, val ex: Throwable) : Outcome<T>(data)
}