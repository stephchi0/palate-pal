package com.example.packpals.repositories

import android.util.Log
import com.example.packpals.models.Pal
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PalsRepository @Inject constructor (private val palsCollectionRef: CollectionReference) {

    companion object {
        val TAG = PalsRepository::class.java.toString()
    }

    suspend fun fetchPal(userId: String): Pal? {
        return try{
            val palReturned = palsCollectionRef.document(userId).get().await()
            palReturned.toObject(Pal::class.java)
        } catch(e: Exception){
            null
        }
    }

    /**
     * queries for pals using userId/username/email
     */
    suspend fun queryPals(query: String): List<Pal> {
        return listOfNotNull(fetchPal(query)).ifEmpty {
            queryPalsByUsername(query).ifEmpty {
                queryPalsByEmail(query).ifEmpty { emptyList() }
            }
        }
    }

    suspend fun queryPalsByUsername(username: String): List<Pal> {
        return try {
            val queryResult = palsCollectionRef.whereEqualTo("name", username).get().await()
            queryResult.toObjects(Pal::class.java)
        } catch (e: Exception) {
            Log.w(TAG, "Error querying pal by username ${username}")
            emptyList()
        }
    }

    suspend fun queryPalsByEmail(email: String): List<Pal> {
        return try {
            val queryResult = palsCollectionRef.whereEqualTo("email", email).get().await()
            queryResult.toObjects(Pal::class.java)
        } catch (e: Exception) {
            Log.w(TAG, "Error querying pal by email ${email}")
            emptyList()
        }
    }

    suspend fun fetchPals(palIds: List<String>): List<Pal> {
        val tripPals = mutableListOf<Pal>()
        for (pal in palIds){
            val palItem = fetchPal(pal)
            if(palItem != null) {
                tripPals.add(palItem)
            }

        }
        return tripPals
    }

    suspend fun createPal(newPal: Pal) {
        val id = newPal.id
        if (!id.isNullOrEmpty()) {
            palsCollectionRef.document(id).set(newPal).await()
        }
    }

    suspend fun editProfile(id: String, name: String, gender: String?, bio: String?) {
        val palProfile = mapOf("name" to name, "gender" to gender, "bio" to bio)
        palsCollectionRef.document(id).update(palProfile).await()
    }
    suspend fun fetchProfile(id: String): Pal? {
        val palEdit = palsCollectionRef.document(id).get().await()
        return if (palEdit.exists()) {
            palEdit.toObject(Pal::class.java)
        } else {
            null
        }
    }

    suspend fun addPal(userId: String): Boolean {
        // TODO: implement
        return false
    }
}