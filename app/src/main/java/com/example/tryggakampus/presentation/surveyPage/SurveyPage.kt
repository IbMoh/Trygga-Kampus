package com.example.tryggakampus.presentation.surveyPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SurveyPage(title: String) {
    Column (modifier = Modifier.fillMaxWidth()) {
        Text(title)
        Text("This is where the questions will appear")
    }
}