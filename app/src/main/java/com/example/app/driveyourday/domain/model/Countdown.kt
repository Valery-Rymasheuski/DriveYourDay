package com.example.app.driveyourday.domain.model

import com.example.app.driveyourday.data.util.EntityId
import com.example.app.driveyourday.util.toMinutesFromMillis

fun Countdown.getLeftMinutes(currentMillis: Long) : Long {
    val elapsedMillis = currentMillis - this.startedTimeMillis
    return if(elapsedMillis > this.durationMillis){
        0
    }else{
        (this.durationMillis - elapsedMillis).toMinutesFromMillis()
    }
}

data class Countdown(
    val id: EntityId,
    val timerId: EntityId,
    val durationMillis: Long,
    val name: String,
    val startedTimeMillis: Long,
)