package com.example.app.driveyourday.ui.screens.login

import android.os.Parcelable
import kotlinx.collections.immutable.ImmutableList
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
    class EnterOptionsStep(val options: ImmutableList<String>) : LoginStep(LoginNavId.LOGIN_OPTIONS)
    class EnterEmailStep(val email: String?) : LoginStep(LoginNavId.LOGIN_EMAIL)
    class EnterPasswordStep() : LoginStep(LoginNavId.LOGIN_PASSWORD)
    object End : LoginStep(LoginNavId.LOGIN_END)
}

enum class LoginNavId {
    LOGIN_OPTIONS,
    LOGIN_EMAIL,
    LOGIN_PASSWORD,
    LOGIN_END,
}