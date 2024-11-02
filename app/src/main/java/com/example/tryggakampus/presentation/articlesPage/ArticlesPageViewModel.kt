package com.example.tryggakampus.presentation.articlesPage

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tryggakampus.dataStore
import com.example.tryggakampus.domain.model.ArticleModel
import com.example.tryggakampus.domain.repository.ArticleRepositoryImpl
import com.google.firebase.firestore.Source
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID

class ArticlesPageViewModel : ViewModel() {
    var articles = mutableStateListOf<ArticleModel>()
        private set
    var deleteMode by mutableStateOf(false)
        private set
    var loadingArticles = mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            ArticleRepositoryImpl.articlesFlow.collect { updatedArticles ->
                articles.clear()
                articles.addAll(updatedArticles)
            }
        }
    }

    private fun setLoadingArticles(b: Boolean) {
        loadingArticles.value = b
    }

    fun loadArticles(context: Context) {
        viewModelScope.launch {
            setLoadingArticles(true)

            val lastFetchTimeKey = longPreferencesKey("articles_last_fetch_time")
            val lastFetchTime: Long = context.dataStore.data
                .map { preferences -> preferences[lastFetchTimeKey] ?: 0L }
                .first()

            val currentTimeMillis = System.currentTimeMillis()
            val timeDifference = (currentTimeMillis - lastFetchTime) / 1000
            val source = if (timeDifference >= 20) {
                Source.SERVER
            } else {
                Source.CACHE
            }

            if (source == Source.SERVER) {
                articles.clear()
            }

            val fetchedArticles = ArticleRepositoryImpl.getAllArticles(source)
            articles.clear()
            articles.addAll(fetchedArticles)

            if (source == Source.SERVER) {
                updateArticlesFetchTime(context)
            }

            setLoadingArticles(false)
        }
    }

    private suspend fun updateArticlesFetchTime(context: Context) {
        val lastFetchTimeKey = longPreferencesKey("articles_last_fetch_time")

        context.dataStore.edit { settings ->
            settings[lastFetchTimeKey] = System.currentTimeMillis()
        }
    }

    fun addArticle(title: String, summary: String, webpage: String) {
        if (title.isBlank() || summary.isBlank() || webpage.isBlank()) {
            Log.d("AddArticleError", "Title, summary, and webpage cannot be empty.")
            return
        }

        viewModelScope.launch {
            val newArticle = ArticleModel(
                id = UUID.randomUUID().toString(),
                title = title,
                summary = summary,
                webpage = webpage
            )
            ArticleRepositoryImpl.addArticle(newArticle)
        }
    }

    fun deleteArticle(article: ArticleModel) {
        viewModelScope.launch {
            try {
                ArticleRepositoryImpl.deleteArticle(article.id)
            } catch (e: Exception) {
                Log.d("DeleteArticleError", "Error deleting article: ${e.localizedMessage}")
            }
        }
    }

    fun toggleDeleteMode() {
        deleteMode = !deleteMode
    }
}


