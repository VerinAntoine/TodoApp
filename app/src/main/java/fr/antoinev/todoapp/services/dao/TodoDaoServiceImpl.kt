package fr.antoinev.todoapp.services.dao

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import fr.antoinev.todoapp.models.Todo
import fr.antoinev.todoapp.services.TodoDaoService
import javax.inject.Inject

class TodoDaoServiceImpl @Inject constructor(): TodoDaoService {

    private var listenerRegistration: ListenerRegistration? = null

    override fun listenByTopic(
        topicId: String,
        onError: (Throwable) -> Unit,
        onDocumentChange: (Boolean, Todo) -> Unit,
    ) {
        listenerRegistration = Firebase.firestore
            .collection(COLLECTION)
            .whereEqualTo(TOPIC_ID, topicId)
            .addSnapshotListener { value, error ->
                onSnapshot(value, error, onError, onDocumentChange)
            }
    }

    override fun listenInOrder(
        userId: String,
        onError: (Throwable) -> Unit,
        onDocumentChange: (Boolean, Todo) -> Unit,
    ) {
        listenerRegistration = Firebase.firestore
            .collection(COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .orderBy(DONE)
            .addSnapshotListener { value, error ->
                onSnapshot(value, error, onError, onDocumentChange)
            }
    }

    override fun listen(
        userId: String,
        onError: (Throwable) -> Unit,
        onDocumentChange: (Boolean, Todo) -> Unit,
    ) {
        listenerRegistration = Firebase.firestore
            .collection(COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .addSnapshotListener { value, error ->
                onSnapshot(value, error, onError, onDocumentChange)
            }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
    }

    override fun find(itemId: String, onError: (Throwable) -> Unit, onSuccess: (Todo) -> Unit) {
        Firebase.firestore
            .collection(COLLECTION)
            .document(itemId)
            .get()
            .addOnFailureListener(onError)
            .addOnSuccessListener {
                val todo = it.toObject<Todo>()?.copy(id = it.id)
                onSuccess(todo ?: Todo())
            }
    }

    override fun update(item: Todo, onComplete: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(COLLECTION)
            .document(item.id)
            .set(item)
            .addOnCompleteListener { onComplete(it.exception) }
    }

    override fun save(item: Todo, onComplete: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(COLLECTION)
            .add(item)
            .addOnCompleteListener { onComplete(it.exception) }
    }

    override fun updateOrSave(item: Todo, onComplete: (Throwable?) -> Unit) {
        if(item.id.isEmpty()) save(item, onComplete)
        else update(item, onComplete)
    }

    override fun delete(itemId: String, onComplete: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(COLLECTION)
            .document(itemId)
            .delete()
            .addOnCompleteListener { onComplete(it.exception) }
    }

    private fun onSnapshot(
        value: QuerySnapshot?,
        error: Throwable?,
        onError: (Throwable) -> Unit,
        onDocumentChange: (Boolean, Todo) -> Unit,
    ) {
        if(error != null) {
            onError(error)
            return
        }

        value?.documentChanges?.forEach {
            val deleted = it.type == DocumentChange.Type.REMOVED
            val todo = it.document.toObject<Todo>().copy(id = it.document.id)
            onDocumentChange(deleted, todo)
        }
    }

    companion object {
        private const val COLLECTION = "todos"
        private const val USER_ID = "userId"
        private const val TOPIC_ID = "topicId"
        private const val DONE = "done"
    }
}