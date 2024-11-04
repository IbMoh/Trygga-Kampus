package com.example.tryggakampus.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SurveyAnswer(
    val question: String,
    val answer: String
)