package com.example.tryggakampus.domain.repository

import android.util.Log
import com.example.tryggakampus.domain.model.StoryModel

import com.google.firebase.firestore.Source

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface StoryRepository {
    suspend fun getAllStories(source: Source): List<StoryModel>
    suspend fun postStory(
        userId: String,
        title: String?,
        content: String,
        isAnonymous: Boolean? = true
    ): Boolean
}

object StoryRepositoryImpl: StoryRepository {
    private const val COLLECTION_NAME = "student-stories"

    override suspend fun getAllStories(source: Source): List<StoryModel> {
        try {
            val result = Firebase.firestore
                .collection(COLLECTION_NAME)
                .get(source)
                .await()

            return result.map { document ->
                val doc = document.toObject(StoryModel::class.java).apply {
                    id = document.id
                }
                doc
            }
        } catch (e: Exception) {
            Log.d("FATAL", e.stackTraceToString())
            return emptyList()
        }
    }

    override suspend fun postStory(
        userId: String,
        title: String?,
        content: String,
        isAnonymous: Boolean?
    ): Boolean {
        try {
            Firebase.firestore.collection(COLLECTION_NAME).add(
                StoryModel(
                    //userId = userId,
                    title = title,
                    content = content,
                    author = if (isAnonymous == false) "Jimmy" else null
                )
            ).await()

            return true
        } catch (e: Exception) {
            Log.d("FATAL", e.stackTraceToString())
            return false
        }
    }
}
