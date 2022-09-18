package fr.antoinev.todoapp.ui.topic

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.antoinev.todoapp.TOPIC_ID
import fr.antoinev.todoapp.TodoAppState
import fr.antoinev.todoapp.ui.topic.edit.TopicEditScreen
import fr.antoinev.todoapp.ui.topic.list.TopicItemScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.topicRoute(appState: TodoAppState) {
    composable(TOPIC_LIST_SCREEN) { TopicItemScreen({ appState.navigate(it) }) }

    composable(
        topicEditScreenRoute("{$TOPIC_ID}"),
        arguments = listOf(navArgument(TOPIC_ID) { nullable = true })
    ) {
        TopicEditScreen(it.arguments?.getString(TOPIC_ID), { appState.popUp() })
    }
}

const val TOPIC_LIST_SCREEN = "topic/list"
const val TOPIC_EDIT_SCREEN = "topic/edit"

fun topicEditScreenRoute(topicId: String): String {
    return "$TOPIC_EDIT_SCREEN?id=$topicId"
}
