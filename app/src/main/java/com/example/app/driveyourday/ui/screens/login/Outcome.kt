package com.example.app.driveyourday.ui.screens.login

sealed class Outcome<T>(open val data: T) {
    data class Loading<T>(override val data: T) : Outcome<T>(data)
    data class Success<T>(override val data: T) : Outcome<T>(data)
    data class Error<T>(override val data: T, val ex: Throwable) : Outcome<T>(data)
}

fun <T1, T2> Outcome<T1>.map(block: (T1) -> T2): Outcome<T2> =
    when (this) {
        is Outcome.Loading -> Outcome.Loading(block(data))
        is Outcome.Success -> Outcome.Success(block(data))
        is Outcome.Error -> Outcome.Error(block(data), ex)
    }