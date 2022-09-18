package fr.antoinev.todoapp.services.impls

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import fr.antoinev.todoapp.services.LogService
import javax.inject.Inject

class LogServiceImpl @Inject constructor() : LogService {

    override fun log(message: String) {
        Firebase.crashlytics.log(message)
    }

    override fun recordThrowable(throwable: Throwable) {
        Firebase.crashlytics.recordException(throwable)
    }
}