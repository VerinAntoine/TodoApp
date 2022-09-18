package fr.antoinev.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.antoinev.todoapp.services.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class TodoAppViewModel(
    private val logService: LogService
): ViewModel() {

    open val recordErrorHandler = CoroutineExceptionHandler() { _, throwable ->
        onError(throwable)
    }

    open fun onError(throwable: Throwable) {
        logService.recordThrowable(throwable)
    }

    fun launchWithHandler(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(recordErrorHandler, block = block)
    }

}
