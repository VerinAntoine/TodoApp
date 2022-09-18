package fr.antoinev.todoapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import fr.antoinev.todoapp.SPLASH_SCREEN
import fr.antoinev.todoapp.rememberAppState
import fr.antoinev.todoapp.todoAppRoute
import fr.antoinev.todoapp.ui.theme.TodoAppTheme

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun TodoAppScreen() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold {
                val appState = rememberAppState()

                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN
                ) { todoAppRoute(appState) }
            }
        }
    }
}
