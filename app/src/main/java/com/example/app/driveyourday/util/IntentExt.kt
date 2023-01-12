package com.example.app.driveyourday.util

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import java.io.Serializable

fun getFlagImmutableCompat() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE
    } else {
        0
    }

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> Intent.getSerializableExtraCompat(name: String, clazz: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(name, clazz)
    } else {
        this.getSerializableExtra(name) as? T
    }