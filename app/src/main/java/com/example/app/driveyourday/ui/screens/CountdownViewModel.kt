package com.example.app.driveyourday.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.driveyourday.domain.model.Countdown
import com.example.app.driveyourday.domain.model.getLeftMinutes
import com.example.app.driveyourday.domain.model.isExpired
import com.example.app.driveyourday.domain.repository.CountdownRepository
import com.example.app.driveyourday.util.ONE_MINUTE
import com.example.app.driveyourday.util.getCurrentMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(private val countdownRepository: CountdownRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CountdownsUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            countdownRepository.getAll().let { list ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        countdowns = mapAtTimeMoment(list)
                    )
                }
            }
            while (true) {
                delay(ONE_MINUTE)
                _uiState.update { state ->
                    state.copy(
                        countdowns = mapAtTimeMoment(state.countdowns
                            .map { it.countdown })
                    )
                }
            }
        }
    }

    private fun mapAtTimeMoment(items: List<Countdown>): List<CountdownAtTimeInfo> {
        val currentMillis = getCurrentMillis()
        return items.map { item ->
            CountdownAtTimeInfo(
                item, item.getLeftMinutes(currentMillis),
                item.isExpired(currentMillis)
            )
        }
    }
}