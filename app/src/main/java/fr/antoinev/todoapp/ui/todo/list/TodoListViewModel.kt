package fr.antoinev.todoapp.ui.todo.list

import androidx.compose.runtime.mutableStateMapOf
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.antoinev.todoapp.models.Todo
import fr.antoinev.todoapp.services.AuthService
import fr.antoinev.todoapp.services.LogService
import fr.antoinev.todoapp.services.TodoDaoService
import fr.antoinev.todoapp.ui.TodoAppViewModel
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoDaoService: TodoDaoService,
    private val authService: AuthService,
    logService: LogService
): TodoAppViewModel(logService) {

    var todos = mutableStateMapOf<String, Todo>()
        private set

    fun listen() {
        launchWithHandler {
            todoDaoService.listen(authService.getUserId(), ::onError, ::onDocumentChange)
        }
    }

    fun removeListener() {
        launchWithHandler { todoDaoService.removeListener() }
    }

    fun onDoneChange(item: Todo, done: Boolean) {
        val todo = item.copy(done = done)
        launchWithHandler {
            todoDaoService.update(todo) {}
        }
    }

    private fun onDocumentChange(deleted: Boolean, item: Todo) {
        if(deleted) todos.remove(item.id) else todos[item.id] = item
    }

}