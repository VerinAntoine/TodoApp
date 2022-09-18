package fr.antoinev.todoapp.services

interface DaoService<T> {

    fun listen(userId: String, onError: (Throwable) -> Unit, onDocumentChange: (Boolean, T) -> Unit)

    fun removeListener()

    fun find(itemId: String, onError: (Throwable) -> Unit, onSuccess: (T) -> Unit)

    fun update(item: T, onComplete: (Throwable?) -> Unit)

    fun save(item: T, onComplete: (Throwable?) -> Unit)

    fun updateOrSave(item: T, onComplete: (Throwable?) -> Unit)

    fun delete(itemId: String, onComplete: (Throwable?) -> Unit)

}