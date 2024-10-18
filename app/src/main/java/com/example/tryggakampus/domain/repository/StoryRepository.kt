package com.example.tryggakampus.domain.repository

import android.util.Log
import com.example.tryggakampus.domain.model.StoryModel
import com.google.firebase.firestore.Source

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface StoryRepository {
    suspend fun getAllStories(source: Source): List<StoryModel>
}


object StoryRepositoryImpl: StoryRepository {
    private const val COLLECTION_NAME = "student-stories"

    override suspend fun getAllStories(source: Source): List<StoryModel> {
        Log.d("FIRESTORE", "Fetching from Source.${source.name}")
        try {
            val result = Firebase.firestore
                .collection(COLLECTION_NAME)
                .get(source)
                .await()

            return result.map { document ->
                document.toObject(StoryModel::class.java)
            }
        } catch (e: Exception) {
            Log.d("FIRESTORE", "Failed to retrieve collection 'student-stories'")
            Log.d("FATAL", e.stackTraceToString())
            return emptyList()
        }
    }
}
