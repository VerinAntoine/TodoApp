package fr.antoinev.todoapp.services

import fr.antoinev.todoapp.models.Todo

interface TodoDaoService: DaoService<Todo> {

    fun listenByTopic(topicId: String, onError: (Throwable) -> Unit, onDocumentChange: (Boolean, Todo) -> Unit)

    fun listenInOrder(userId: String, onError: (Throwable) -> Unit, onDocumentChange: (Boolean, Todo) -> Unit)

}