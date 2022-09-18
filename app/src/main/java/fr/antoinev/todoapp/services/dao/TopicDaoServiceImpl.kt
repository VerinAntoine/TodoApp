package fr.antoinev.todoapp.services.dao

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import fr.antoinev.todoapp.models.Topic
import fr.antoinev.todoapp.services.DaoService
import javax.inject.Inject

class TopicDaoServiceImpl @Inject constructor() : DaoService<Topic> {

    private var listenerRegistration: ListenerRegistration? = null

    override fun listen(
        userId: String,
        onError: (Throwable) -> Unit,
        onDocumentChange: (Boolean, Topic) -> Unit
    ) {
        listenerRegistration = Firebase.firestore
            .collection(TOPIC_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .addSnapshotListener { value, error ->
                if(error != null) {
                    onError(error)
                    return@addSnapshotListener
                }

                value?.documentChanges?.forEach {
                    val deleted = it.type == DocumentChange.Type.REMOVED
                    val topic = it.document.toObject<Topic>().copy(id = it.document.id)
                    onDocumentChange(deleted, topic)
                }
            }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
    }

    override fun find(itemId: String, onError: (Throwable) -> Unit, onSuccess: (Topic) -> Unit) {
        Firebase.firestore
            .collection(TOPIC_COLLECTION)
            .document(itemId)
            .get()
            .addOnFailureListener(onError)
            .addOnSuccessListener {
                val topic = it.toObject<Topic>()?.copy(id = it.id)
                onSuccess(topic ?: Topic())
            }
    }

    override fun updateOrSave(item: Topic, onComplete: (Throwable?) -> Unit) {
        if(item.id.isEmpty()) save(item, onComplete)
        else update(item, onComplete)
    }

    override fun update(item: Topic, onComplete: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(TOPIC_COLLECTION)
            .document(item.id)
            .set(item)
            .addOnCompleteListener { onComplete(it.exception) }
    }

    override fun save(item: Topic, onComplete: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(TOPIC_COLLECTION)
            .add(item)
            .addOnCompleteListener { onComplete(it.exception) }
    }

    override fun delete(itemId: String, onComplete: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(TOPIC_COLLECTION)
            .document(itemId)
            .delete()
            .addOnCompleteListener { onComplete(it.exception) }
    }

    companion object {
        private const val TOPIC_COLLECTION = "topics"
        private const val USER_ID = "userId"
    }
}