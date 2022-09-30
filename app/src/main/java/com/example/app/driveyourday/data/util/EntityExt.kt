package com.example.app.driveyourday.data.util

fun Long.isValidEntityId() = this > 0

fun Long.isNotValidEntityId() = this <= 0