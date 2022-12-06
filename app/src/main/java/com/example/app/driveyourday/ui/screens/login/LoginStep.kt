package com.example.app.driveyourday.ui.screens.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class LoginFlowType {
    SOCIAL, EMAIL
}

@Parcelize
data class LoginFilledData(
    val flowType: LoginFlowType?,
    val userEmail: String?,
    val userPassword: String?,
    val termsConfirmed: Boolean,
    val userId: String?,
) : Parcelable

/**
 *  Should be class, not object or data class. This is because we need recomposition to happen everytime an event is emitted.
 *  Recompositions is emitted if input is the same (uses equals()), and having a new instance (class) the equals() will return false.
 */
sealed class LoginStep(val id: LoginNavId) {
    object Start : LoginStep(LoginNavId.START)
    class EnterOptionsStep(val options: List<String>) : LoginStep(LoginNavId.OPTIONS)
    class EnterEmailStep(val email: String?) : LoginStep(LoginNavId.EMAIL)
    class EnterPasswordStep() : LoginStep(LoginNavId.PASSWORD)
    object End : LoginStep(LoginNavId.END)
}

enum class LoginNavId {
    START,
    OPTIONS,
    EMAIL,
    PASSWORD,
    END,
}