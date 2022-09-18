package fr.antoinev.todoapp.models

data class Todo(
    val id: String = "",
    val title: String = "",
    val done: Boolean = false,
    val userId: String = "",
)
