package fr.antoinev.todoapp.ui.login

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.antoinev.todoapp.services.AuthService
import fr.antoinev.todoapp.services.LogService
import fr.antoinev.todoapp.ui.TodoAppViewModel
import fr.antoinev.todoapp.ui.todo.TODO_LIST_SCREEN
import fr.antoinev.todoapp.ui.topic.TOPIC_LIST_SCREEN
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authService: AuthService,
    private val logService: LogService
): TodoAppViewModel(logService) {

    var state = mutableStateOf(LoginScreenState())
        private set

    var error = mutableStateOf<AuthService.AuthError?>(null)
        private set

    fun onPhoneNumberChange(phoneNumber: String) {
        state.value = state.value.copy(phoneNumber = phoneNumber)
    }

    fun onPhoneNumberSent(activity: Activity, navigate: (String) -> Unit) {
        error.value = null
        state.value = state.value.copy(currentStep = LoginScreenStep.PHONE_NUMBER_VERIFICATION)

        authService.verifyPhoneNumber(
            phoneNumber = state.value.phoneNumber,
            activity = activity,
            onError = { type, throwable ->
                state.value = state.value.copy(currentStep = LoginScreenStep.PHONE_NUMBER)
                error.value = type

                if(type == AuthService.AuthError.UNKNOWN) {
                    logService.log(type.name)
                    onError(throwable)
                }
            },
            onSuccess = { onLoggedIn(navigate) }
        ) { state.value = state.value.copy(currentStep = LoginScreenStep.VERIFICATION_CODE) }
    }

    fun onVerificationCodeChange(verificationCode: String) {
        state.value = state.value.copy(verificationCode = verificationCode)
    }

    fun onVerificationCodeSent(navigate: (String) -> Unit) {
        error.value = null

        authService.verifyVerificationCode(
            verificationCode = state.value.verificationCode,
            onError = { type, throwable ->
                error.value = type

                if(error.value == AuthService.AuthError.UNKNOWN) {
                    logService.log(type.name)
                    onError(throwable)
                }
            }
        ) { onLoggedIn(navigate) }
    }

    private fun onLoggedIn(navigate: (String) -> Unit) {
        navigate(TODO_LIST_SCREEN)
    }

}