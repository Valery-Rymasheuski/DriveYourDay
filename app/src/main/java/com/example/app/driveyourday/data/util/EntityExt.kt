package com.example.app.driveyourday.data.util

typealias EntityId = Long

const val ENTITY_EMPTY_ID = 0L

fun Long.isValidEntityId() = this > ENTITY_EMPTY_ID

fun Long.isNotValidEntityId() = this <= ENTITY_EMPTY_ID