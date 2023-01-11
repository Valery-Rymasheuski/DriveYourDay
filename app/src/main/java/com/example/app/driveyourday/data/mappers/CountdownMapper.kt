package com.example.app.driveyourday.data.mappers

import com.example.app.driveyourday.data.local.entity.CountdownEntity
import com.example.app.driveyourday.domain.model.Countdown
import javax.inject.Inject

class CountdownMapper @Inject constructor() : EntityMapper<CountdownEntity, Countdown> {

    override fun fromEntity(entity: CountdownEntity): Countdown =
        Countdown(
            id = entity.id,
            timerId = entity.timerId,
            durationMillis = entity.durationMillis,
            name = entity.name,
            startedTimeMillis = entity.startedTimeMillis
        )

    override fun toEntity(domain: Countdown): CountdownEntity = CountdownEntity(
        timerId = domain.timerId,
        durationMillis = domain.durationMillis,
        startedTimeMillis = domain.startedTimeMillis,
        name = domain.name,
        id = domain.id
    )
}