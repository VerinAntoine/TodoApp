package fr.antoinev.todoapp.ui.login

data class LoginScreenState(
    val currentStep: LoginScreenStep = LoginScreenStep.PHONE_NUMBER,
    val phoneNumber: String = "",
    val verificationCode: String = "",
)

enum class LoginScreenStep {
    PHONE_NUMBER,
    PHONE_NUMBER_VERIFICATION,
    VERIFICATION_CODE,
}