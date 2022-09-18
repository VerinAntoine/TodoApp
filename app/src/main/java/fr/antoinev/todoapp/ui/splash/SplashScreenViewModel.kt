package fr.antoinev.todoapp.ui.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import fr.antoinev.todoapp.LOGIN_SCREEN
import fr.antoinev.todoapp.services.AuthService
import fr.antoinev.todoapp.services.LogService
import fr.antoinev.todoapp.ui.TodoAppViewModel
import fr.antoinev.todoapp.ui.todo.TODO_LIST_SCREEN
import fr.antoinev.todoapp.ui.topic.TOPIC_LIST_SCREEN
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authService: AuthService,
    logService: LogService
): TodoAppViewModel(logService) {

    fun doRouting(navigate: (String) -> Unit) {
        navigate(if(authService.isLogged()) TODO_LIST_SCREEN else LOGIN_SCREEN)
    }

}