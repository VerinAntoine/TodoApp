package fr.antoinev.todoapp.ui.todo.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.antoinev.todoapp.models.Todo
import fr.antoinev.todoapp.services.AuthService
import fr.antoinev.todoapp.services.LogService
import fr.antoinev.todoapp.services.TodoDaoService
import fr.antoinev.todoapp.ui.TodoAppViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val todoDaoService: TodoDaoService,
    private val authService: AuthService,
    logService: LogService
): TodoAppViewModel(logService) {

    var todo = mutableStateOf(Todo())
        private set

    fun findTodo(todoId: String) {
        viewModelScope.launch(recordErrorHandler) {
            todoDaoService.find(todoId, ::onError) {
                todo.value = it
            }
        }
    }

    fun saveTodo(popUp: () -> Unit) {
        todo.value = todo.value.copy(userId = authService.getUserId())
        viewModelScope.launch(recordErrorHandler) {
            todoDaoService.updateOrSave(todo.value) { error ->
                if(error != null) onError(error)
                else popUp()
            }
        }
    }

    fun onTitleChange(title: String) {
        todo.value = todo.value.copy(title = title)
    }

    fun onDoneChange(done: Boolean) {
        todo.value = todo.value.copy(done = done)
    }

}