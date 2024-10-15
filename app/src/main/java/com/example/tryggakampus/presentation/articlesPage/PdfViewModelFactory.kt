package com.example.tryggakampus.presentation.articlesPage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tryggakampus.domain.repository.ArticlesPageRepository

class PdfViewModelFactory(private val repository: ArticlesPageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticlesPageViewModel::class.java)) {
            return ArticlesPageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

