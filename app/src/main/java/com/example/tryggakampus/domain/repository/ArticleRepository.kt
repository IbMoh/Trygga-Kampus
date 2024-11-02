package com.example.tryggakampus.domain.repository

import android.util.Log
import com.example.tryggakampus.domain.model.ArticleModel
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ArticleRepository {
    suspend fun getAllArticles(source: Source): List<ArticleModel>
    suspend fun addArticle(article: ArticleModel)
    suspend fun deleteArticle(articleId: String)
    suspend fun fetchAll(source: Source): QuerySnapshot?
}


object ArticleRepositoryImpl : ArticleRepository {
    private const val COLLECTION_NAME = "articles"

    private val _articlesFlow = MutableStateFlow<List<ArticleModel>>(emptyList())
    val articlesFlow: StateFlow<List<ArticleModel>> get() = _articlesFlow

    override suspend fun getAllArticles(source: Source): List<ArticleModel> {
        val result = this.fetchAll(source)
        val articles = result?.map { document ->
            document.toObject(ArticleModel::class.java).apply {
                id = document.id
            }
        } ?: emptyList()

        _articlesFlow.value = articles
        return articles
    }

    override suspend fun fetchAll(source: Source): QuerySnapshot? {
        val ref = Firebase.firestore.collection(COLLECTION_NAME)

        return try {
            if (source == Source.SERVER) {
                Log.d("ArticleRepository::fetchAll", "Trying to fetch from SERVER")
            }
            ref.get(source).await()
        } catch (e: FirebaseException) {
            Log.d("ArticleRepository::fetchAll", "Trying to fetch from CACHE, Server connection failed: ${e.message}")
            ref.get(Source.CACHE).await()
        } catch (e: Exception) {
            Log.d("ArticleRepository::fetchAll", "General error: ${e.message}")
            null
        }
    }

    override suspend fun addArticle(article: ArticleModel) {
        val updatedArticles = _articlesFlow.value.toMutableList().apply { add(article) }
        _articlesFlow.value = updatedArticles

        try {
            Firebase.firestore
                .collection(COLLECTION_NAME)
                .document(article.id)
                .set(article)
                .await()
        } catch (e: Exception) {
            Log.d("AddArticleError", "Failed to add article: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun deleteArticle(articleId: String) {
        val updatedArticles = _articlesFlow.value.toMutableList().apply {
            removeAll { it.id == articleId }
        }
        _articlesFlow.value = updatedArticles

        try {
            Firebase.firestore
                .collection(COLLECTION_NAME)
                .document(articleId)
                .delete()
                .await()
        } catch (e: Exception) {
            Log.d("DeleteArticleError", "Failed to delete article: ${e.localizedMessage}")
            throw e
        }
    }
}
