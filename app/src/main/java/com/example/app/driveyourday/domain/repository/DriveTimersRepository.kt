package com.example.app.driveyourday.domain.repository

import com.example.app.driveyourday.domain.model.DriveTimer

interface DriveTimersRepository {

    fun getTimers(): List<DriveTimer>
}