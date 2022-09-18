package fr.antoinev.todoapp.ui.topic.list

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.antoinev.todoapp.models.Topic
import fr.antoinev.todoapp.services.AuthService
import fr.antoinev.todoapp.services.DaoService
import fr.antoinev.todoapp.services.LogService
import fr.antoinev.todoapp.ui.TodoAppViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
    private val topicService: DaoService<Topic>,
    private val authService: AuthService,
    logService: LogService
): TodoAppViewModel(logService) {

    var topics = mutableStateMapOf<String, Topic>()
        private set

    fun listen() {
        viewModelScope.launch(recordErrorHandler) {
            topicService.listen(authService.getUserId(), ::onError, ::onDocumentChange)
        }
    }

    fun removeListener() {
        viewModelScope.launch(recordErrorHandler) { topicService.removeListener() }
    }

    private fun onDocumentChange(deleted: Boolean, item: Topic) {
        if(deleted) topics.remove(item.id)
        else topics[item.id] = item
    }

}