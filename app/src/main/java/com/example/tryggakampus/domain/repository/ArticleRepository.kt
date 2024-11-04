package com.example.tryggakampus.domain.repository

import android.util.Log
import com.example.tryggakampus.domain.model.ArticleModel
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow

interface ArticleRepository {
    enum class RepositoryResult {
        SUCCESS,
        ERROR_NETWORK,
        ERROR_DATABASE,
        ERROR_UNKNOWN
    }

    suspend fun getAllArticles(source: Source): Pair<RepositoryResult, List<ArticleModel>>
    suspend fun addArticle(article: ArticleModel): RepositoryResult
    suspend fun deleteArticle(articleId: String): RepositoryResult
    suspend fun fetchAll(source: Source): QuerySnapshot?
}

object ArticleRepositoryImpl : ArticleRepository {
    private const val COLLECTION_NAME = "articles"
    val _articlesFlow = MutableStateFlow<List<ArticleModel>>(emptyList())

    override suspend fun getAllArticles(source: Source): Pair<ArticleRepository.RepositoryResult, List<ArticleModel>> {
        val result = fetchAll(source)
        return if (result != null) {
            val articles = result.map { document ->
                document.toObject(ArticleModel::class.java).apply { id = document.id }
            }
            _articlesFlow.value = articles
            ArticleRepository.RepositoryResult.SUCCESS to articles
        } else {
            ArticleRepository.RepositoryResult.ERROR_DATABASE to emptyList()
        }
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

    override suspend fun addArticle(article: ArticleModel): ArticleRepository.RepositoryResult {
        val updatedArticles = _articlesFlow.value.toMutableList().apply { add(article) }
        _articlesFlow.value = updatedArticles

        return try {
            Firebase.firestore
                .collection(COLLECTION_NAME)
                .document(article.id)
                .set(article)
                .await()
            ArticleRepository.RepositoryResult.SUCCESS
        } catch (e: FirebaseFirestoreException) {
            Log.d("AddArticleError", "Network or database error: ${e.message}")
            ArticleRepository.RepositoryResult.ERROR_NETWORK
        } catch (e: Exception) {
            Log.d("AddArticleError", "Unknown error: ${e.stackTraceToString()}")
            ArticleRepository.RepositoryResult.ERROR_UNKNOWN
        }
    }

    override suspend fun deleteArticle(articleId: String): ArticleRepository.RepositoryResult {
        val updatedArticles = _articlesFlow.value.toMutableList().apply {
            removeAll { it.id == articleId }
        }
        _articlesFlow.value = updatedArticles

        return try {
            Firebase.firestore
                .collection(COLLECTION_NAME)
                .document(articleId)
                .delete()
                .await()
            ArticleRepository.RepositoryResult.SUCCESS
        } catch (e: FirebaseFirestoreException) {
            Log.d("DeleteArticleError", "Network or database error: ${e.message}")
            ArticleRepository.RepositoryResult.ERROR_NETWORK
        } catch (e: Exception) {
            Log.d("DeleteArticleError", "Unknown error: ${e.stackTraceToString()}")
            ArticleRepository.RepositoryResult.ERROR_UNKNOWN
        }
    }
}

