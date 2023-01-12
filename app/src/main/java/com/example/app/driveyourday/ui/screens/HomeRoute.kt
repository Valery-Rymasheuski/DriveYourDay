package com.example.app.driveyourday.ui.screens

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.example.app.driveyourday.R
import com.example.app.driveyourday.domain.model.Countdown
import com.example.app.driveyourday.ui.MainActivity
import com.example.app.driveyourday.ui.receivers.CountdownReceiver
import com.example.app.driveyourday.util.getFlagImmutableCompat
import de.palm.composestateevents.EventEffect

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    EventEffect(event = uiState.addedTimerEvent,
        onConsumed = { viewModel.setAddedTimerEventConsumed() },
        action = { timerName ->
            snackbarHostState.showSnackbar(
                context.resources.getString(
                    R.string.msg_added_timer,
                    timerName
                )
            )
        })
    EventEffect(event = uiState.startedTimerEvent,
        onConsumed = { viewModel.setStartedTimerEventConsumed() },
        action = { countdown ->
            startAlarm(countdown, context)
            snackbarHostState.showSnackbar(
                context.resources.getString(
                    R.string.msg_started_timer,
                    countdown.name
                )
            )
        })
    HomeScreen(uiState, onTimerClick = { viewModel.startTimer(it) }) //TODO remove HomeRoute ?
}

private fun startAlarm(countdown: Countdown, ctx: Context) {
    val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    //alarmManager.canScheduleExactAlarms() //TODO

    val pendingIntent = Intent(ctx, CountdownReceiver::class.java)
        .apply {
            putExtra(CountdownReceiver.EXTRA_COUNTDOWN, countdown)
        }.let { intent ->
            PendingIntent.getBroadcast(
                ctx,
                countdown.id.toInt(),
                intent,
                getFlagImmutableCompat()
            )
        }

    // when using setAlarmClock() it displays a notification until alarm rings and when pressed it takes us to MainActivity
    val showPendingIntent = Intent(ctx, MainActivity::class.java)
        .let { showIntent ->
            PendingIntent.getActivity(
                ctx,
                countdown.id.toInt(),
                showIntent,
                getFlagImmutableCompat()
            )

        }
    val triggerTime = System.currentTimeMillis() + countdown.durationMillis
    val alarmClockInfo = AlarmManager.AlarmClockInfo(triggerTime, showPendingIntent)
    alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
}