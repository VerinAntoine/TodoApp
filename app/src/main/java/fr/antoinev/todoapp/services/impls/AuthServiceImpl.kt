package fr.antoinev.todoapp.services.impls

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.antoinev.todoapp.services.AuthService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthServiceImpl @Inject constructor() : AuthService {

    private var verificationId: String? = null
    private var token: PhoneAuthProvider.ForceResendingToken? = null

    override fun isLogged(): Boolean {
        return Firebase.auth.uid != null
    }

    override fun getUserId(): String {
        return Firebase.auth.uid ?: ""
    }

    override fun verifyPhoneNumber(
        phoneNumber: String,
        activity: Activity,
        onError: (AuthService.AuthError, Throwable) -> Unit,
        onSuccess: () -> Unit,
        onCodeSent: () -> Unit,
    ) {
        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signIn(credential, onError, onSuccess)
                }

                override fun onVerificationFailed(error: FirebaseException) {
                    if(error is FirebaseAuthInvalidCredentialsException)
                        onError(AuthService.AuthError.INVALID_CREDENTIALS, error)
                    else if(error is FirebaseTooManyRequestsException)
                        onError(AuthService.AuthError.OUT_OF_QUOTA, error)
                    else
                        onError(AuthService.AuthError.UNKNOWN, error)
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    this@AuthServiceImpl.verificationId = verificationId
                    this@AuthServiceImpl.token = token
                    onCodeSent()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyVerificationCode(
        verificationCode: String,
        onError: (AuthService.AuthError, Throwable) -> Unit,
        onSuccess: () -> Unit
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, verificationCode)
        signIn(credential, onError, onSuccess)
    }

    private fun signIn(
        credential: PhoneAuthCredential,
        onError: (AuthService.AuthError, Throwable) -> Unit,
        onSuccess: () -> Unit
    ) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    onSuccess()
                } else {
                    if(task.exception is FirebaseAuthInvalidCredentialsException)
                        onError(
                            AuthService.AuthError.INVALID_VERIFICATION_CODE,
                            task.exception as FirebaseAuthInvalidCredentialsException
                        )
                    else
                        task.exception?.let { onError(AuthService.AuthError.UNKNOWN, it) }
                }
            }
    }

}