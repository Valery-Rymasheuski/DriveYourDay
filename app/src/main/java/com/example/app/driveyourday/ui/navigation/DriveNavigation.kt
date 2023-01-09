package com.example.app.driveyourday.ui.navigation

import androidx.annotation.StringRes
import com.example.app.driveyourday.R

enum class DriveDestinations(@StringRes val titleResId: Int) {
    HOME(R.string.app_name),
    SETTINGS(R.string.screen_settings),
    EDIT_TIMERS(R.string.screen_edit_timers),
    ADD_TIMER(R.string.screen_add_timer),
    EDIT_TIMER_GROUPS(R.string.screen_edit_timer_groups),
    ADD_TIMER_GROUP(R.string.screen_add_timer_group)
}