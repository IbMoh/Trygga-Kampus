package com.example.tryggakampus.data

import com.example.tryggakampus.domain.model.SurveyAnswer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SurveyRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun submitSurveyAnswers(answers: List<SurveyAnswer>) {
        try {
            val surveyCollection = db.collection("Student-Survey-Answers")
            answers.forEach { answer ->
                val answerMap = mapOf(
                    "question" to answer.question,
                    "answer" to answer.answer
                )
                surveyCollection.add(answerMap).await()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}