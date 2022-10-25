package com.example.app.driveyourday.data.util

const val ENTITY_EMPTY_ID = 0L

fun Long.isValidEntityId() = this > ENTITY_EMPTY_ID

fun Long.isNotValidEntityId() = this <= ENTITY_EMPTY_ID