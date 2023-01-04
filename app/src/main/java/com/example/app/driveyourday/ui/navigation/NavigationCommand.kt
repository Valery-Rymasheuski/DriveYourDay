package com.example.app.driveyourday.ui.navigation

data class NavigationCommand(
    val destination: String,
    val launchSingleTop: Boolean = false,
    val popUpTo: String? = null,
    val inclusive: Boolean = false,
    val skipSameRoute: Boolean = false,
) {
    fun isNotEmpty() = this != EMPTY

    companion object {
        private const val EMPTY_DESTINATION = ""

        val EMPTY = NavigationCommand(EMPTY_DESTINATION)
    }
}
