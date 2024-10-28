package com.example.tryggakampus.domain.repository

import android.util.Log
import com.example.tryggakampus.domain.model.StoryModel
import com.google.firebase.firestore.QuerySnapshot

import com.google.firebase.firestore.Source

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait

interface StoryRepository {
    suspend fun getAllStories(source: Source): List<StoryModel>
    suspend fun postStory(
        // userId: String,
        title: String?,
        content: String,
        isAnonymous: Boolean? = true
    ): StoryModel?

    suspend fun fetchAll(source: Source): QuerySnapshot
}

object StoryRepositoryImpl: StoryRepository {
    private const val COLLECTION_NAME = "student-stories"

    override suspend fun getAllStories(source: Source): List<StoryModel> {
        val result = this.fetchAll(source)

        return result.map { document ->
            val doc = document.toObject(StoryModel::class.java).apply {
                id = document.id
            }
            doc
        }
    }

    override suspend fun fetchAll(source: Source): QuerySnapshot {
        var result: QuerySnapshot
        val ref = Firebase.firestore.collection(COLLECTION_NAME)

        try {
            if (source == Source.SERVER) {
                Log.d("StoryRepository::fetchAll", "Trying to fetch from SERVER")
            }
            result = ref.get(source).await()
        } catch (e: Exception) {
            Log.d("StoryRepository::fetchAll", "Trying to fetch from CACHE, Server connection failed.")
            result = ref.get(Source.CACHE).await()
        }

        return result
    }

    override suspend fun postStory(
        // userId: String,
        title: String?,
        content: String,
        isAnonymous: Boolean?
    ): StoryModel? {
        try {
            val result = Firebase.firestore.collection(COLLECTION_NAME).add(
                StoryModel(
                    //userId = userId,
                    title = title,
                    content = content,
                    author = if (isAnonymous == false) "Jimmy" else null
                )
            ).await()

            return result.get().await().toObject(StoryModel::class.java)?.apply {
                this.id = result.id
            }
        } catch (e: Exception) {
            Log.d("FATAL", e.stackTraceToString())
            return null
        }
    }
}
