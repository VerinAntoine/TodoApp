package fr.antoinev.todoapp.services

import android.app.Activity

interface AuthService {

    fun isLogged(): Boolean

    fun getUserId(): String

    fun verifyPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        onError: (AuthError, Throwable) -> Unit,
        onSuccess: () -> Unit,
        onCodeSent: () -> Unit
    )

    fun verifyVerificationCode(
        verificationCode: String,
        onError: (AuthError, Throwable) -> Unit,
        onSuccess: () -> Unit
    )

    enum class AuthError {
        INVALID_VERIFICATION_CODE,
        INVALID_CREDENTIALS,
        OUT_OF_QUOTA,
        UNKNOWN
    }

}