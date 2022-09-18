package fr.antoinev.todoapp.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.getActivity(): Activity? = when(this) {
    is Activity -> this
    is ContextWrapper -> this.getActivity()
    else -> null
}
