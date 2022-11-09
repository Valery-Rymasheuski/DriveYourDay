package com.example.app.driveyourday

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DriveYourDayApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            System.setProperty(
                kotlinx.coroutines.DEBUG_PROPERTY_NAME,
                kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON
            )
        }
    }
}