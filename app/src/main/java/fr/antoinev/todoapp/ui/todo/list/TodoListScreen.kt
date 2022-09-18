package fr.antoinev.todoapp.ui.todo.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fr.antoinev.todoapp.ui.todo.TODO_EDIT_SCREEN

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun TodoListScreen(
    navigate: (String) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel(),
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigate(TODO_EDIT_SCREEN) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Create a todo")
            }
        }
    ) {
        Column {
            val todos = viewModel.todos

            CenterAlignedTopAppBar(
                title = { Text("TodoApp") },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Go to the settings")
                    }
                }
            )

            LazyColumn {
                items(todos.values.toList().sortedBy { it.done }, key = { it.id }) {
                    TodoItem(
                        item = it,
                        onCheckedChange = { done -> viewModel.onDoneChange(it, done) },
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }

    DisposableEffect(viewModel) {
        viewModel.listen()
        onDispose { viewModel.removeListener() }
    }
}
