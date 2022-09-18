package fr.antoinev.todoapp.ui.todo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.antoinev.todoapp.TODO_ID
import fr.antoinev.todoapp.TodoAppState
import fr.antoinev.todoapp.ui.todo.edit.TodoEditScreen
import fr.antoinev.todoapp.ui.todo.list.TodoListScreen

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.todoRoute(appState: TodoAppState) {
    composable(TODO_LIST_SCREEN) {
        TodoListScreen(
            navigate = { route -> appState.navigate(route) },
        )
    }

    composable(
        todoEditScreenRoute("{$TODO_ID}"),
        arguments = listOf(navArgument(TODO_ID) { nullable = true })
    ) {
        TodoEditScreen(todoId = it.arguments?.getString(TODO_ID), popUp = { appState.popUp() })
    }
}

const val TODO_LIST_SCREEN = "todo/list"
const val TODO_EDIT_SCREEN = "todo/edit"

fun todoEditScreenRoute(todoId: String): String {
    return "$TODO_EDIT_SCREEN?id=$todoId"
}
