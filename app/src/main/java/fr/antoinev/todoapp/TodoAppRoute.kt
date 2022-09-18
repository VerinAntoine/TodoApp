package fr.antoinev.todoapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fr.antoinev.todoapp.ui.login.LoginScreen
import fr.antoinev.todoapp.ui.splash.SplashScreen
import fr.antoinev.todoapp.ui.todo.todoRoute
import fr.antoinev.todoapp.ui.topic.topicRoute

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.todoAppRoute(appState: TodoAppState) {
    topicRoute(appState)
    todoRoute(appState)

    composable(SPLASH_SCREEN) { SplashScreen({ appState.navigateAndPopUp(it, SPLASH_SCREEN) }) }
    composable(LOGIN_SCREEN) { LoginScreen({ appState.navigateAndPopUp(it, LOGIN_SCREEN) }) }
}

@Composable
fun Wip() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Working progress")
    }
}

const val SPLASH_SCREEN = "splash"
const val LOGIN_SCREEN = "login"

const val TOPIC_ID = "topicId"
const val TODO_ID = "todoId"
