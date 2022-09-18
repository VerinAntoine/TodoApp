package fr.antoinev.todoapp.ui.todo.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.antoinev.todoapp.extensions.mediumSpacer

@ExperimentalMaterial3Api
@Composable
fun TodoEditScreen(
    todoId: String?,
    popUp: () -> Unit,
    viewModel: TodoEditViewModel = hiltViewModel(),
) {
    val todo by viewModel.todo

    Column(modifier = Modifier.padding(10.dp, 0.dp)) {
        SmallTopAppBar(
            title = { Text(if(todoId == null) "Create todo" else "Edit todo") },
            navigationIcon = {
                IconButton(onClick = { popUp() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back")
                }
            },
            actions = {
                IconButton(onClick = { viewModel.saveTodo(popUp = { popUp() }) }) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Save")
                }
            }
        )

        OutlinedTextField(
            value = todo.title,
            onValueChange = { viewModel.onTitleChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true
            )
        )
        Spacer(modifier = Modifier.mediumSpacer())

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Done")
            Checkbox(checked = todo.done, onCheckedChange = { viewModel.onDoneChange(it) })
        }
    }

    LaunchedEffect(Unit) {
        if(todoId != null) viewModel.findTodo(todoId)
    }
}
