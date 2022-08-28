package fr.antoinev.todoapp

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TodoApp: Application() {

    override fun onCreate() {
        if(!BuildConfig.IS_PRODUCTION) {
            // Use emulator server ip
            Firebase.firestore.useEmulator("192.168.1.26", 8080)
            Firebase.firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()

            Firebase.auth.useEmulator("192.168.1.26", 9099)
        }

        super.onCreate()
    }

}