package fr.antoinev.todoapp.ui.topic.edit

import androidx.compose.runtime.mutableStateOf
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
class TopicEditViewModel @Inject constructor(
    private val daoService: DaoService<Topic>,
    private val authService: AuthService,
    logService: LogService
): TodoAppViewModel(logService) {

    var topic = mutableStateOf(Topic())
        private set

    fun findTopic(topicId: String) {
        viewModelScope.launch(recordErrorHandler) {
            daoService.find(topicId, onError = { onError(it) }) {
                topic.value = it
            }
        }
    }

    fun onFinish(popUp: () -> Unit) {
        topic.value = topic.value.copy(userId = authService.getUserId())
        viewModelScope.launch(recordErrorHandler) {
            daoService.updateOrSave(topic.value) { popUp() }
        }
    }

    fun onNameChange(name: String) {
        topic.value = topic.value.copy(name = name)
    }

}