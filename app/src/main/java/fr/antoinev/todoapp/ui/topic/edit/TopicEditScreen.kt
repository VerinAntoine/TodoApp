package fr.antoinev.todoapp.ui.topic.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.antoinev.todoapp.extensions.mediumSpacer

@ExperimentalMaterial3Api
@Composable
fun TopicEditScreen(
    topicId: String?,
    popUp: () -> Unit,
    viewModel: TopicEditViewModel = hiltViewModel(),
) {
    val topic by viewModel.topic

    LaunchedEffect(Unit) {
        if(topicId != null) viewModel.findTopic(topicId)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp, 0.dp)) {
        SmallTopAppBar(
            title = { Text(if(topicId == null) "Create a topic" else "Edit a topic") },
            navigationIcon = {
                IconButton(onClick = { popUp() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back")
                }
            },
            actions = {
                IconButton(onClick = { viewModel.onFinish(popUp) }) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Finish")
                }
            }
        )
        OutlinedTextField(
            value = topic.name,
            onValueChange = { viewModel.onNameChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Title") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.mediumSpacer())
    }
}
