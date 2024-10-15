package com.example.tryggakampus.presentation.articlesPage

import androidx.lifecycle.ViewModel
import com.example.tryggakampus.domain.repository.ArticlesPageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticlesPageViewModel(private val repository: ArticlesPageRepository) : ViewModel() {

    private val _pdfUrls = MutableStateFlow<List<String>>(emptyList())
    val pdfUrls: StateFlow<List<String>> = _pdfUrls

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchPdfUrls()
    }

    private fun fetchPdfUrls() {
        _isLoading.value = true

        repository.fetchAllPdfUrls { urls ->
            _pdfUrls.value = urls
            _isLoading.value = false
        }
    }
}
