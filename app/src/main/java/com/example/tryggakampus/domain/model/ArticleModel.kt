package com.example.tryggakampus.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleModel(
    val id: Int = 0,
    val title: String? = null,
    val summary: String = "No summary",
    val webpage: String? = null,
)
