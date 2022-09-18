package fr.antoinev.todoapp.services

interface LogService {

    fun log(message: String)

    fun recordThrowable(throwable: Throwable)

}