package fr.antoinev.todoapp.ui.topic.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import fr.antoinev.todoapp.ui.topic.TOPIC_EDIT_SCREEN

@ExperimentalMaterial3Api
@Composable
fun TopicItemScreen(
    navigate: (String) -> Unit,
    viewModel: TopicListViewModel = hiltViewModel(),
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigate(TOPIC_EDIT_SCREEN) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Create")
            }
        }
    ) {
        val topics = viewModel.topics

        Column {
            CenterAlignedTopAppBar(
                title = { Text(text = "TodoApp") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
            LazyColumn {
                items(topics.values.toList(), key = { it.id }) { topicItem ->
                    TopicListScreen(topicItem, navigate)
                }
            }
        }

        DisposableEffect(viewModel) {
            viewModel.listen()
            onDispose { viewModel.removeListener() }
        }
    }
}
