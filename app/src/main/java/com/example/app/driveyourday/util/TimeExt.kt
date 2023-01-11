package com.example.app.driveyourday.util

import android.os.SystemClock

const val ONE_MINUTE = 60 * 1000L

fun Long.toMillisFromMinutes() = this * ONE_MINUTE

fun Long.toMinutesFromMillis() = this / ONE_MINUTE

fun getCurrentMillis() = SystemClock.elapsedRealtime()

