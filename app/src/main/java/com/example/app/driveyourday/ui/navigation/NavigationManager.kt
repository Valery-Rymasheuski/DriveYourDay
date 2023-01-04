package com.example.app.driveyourday.ui.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager {

    private val _commands = MutableStateFlow(NavigationCommand.EMPTY)
    val commands = _commands.asStateFlow()

    fun navigate(command: NavigationCommand) {
        _commands.value = command
    }
}