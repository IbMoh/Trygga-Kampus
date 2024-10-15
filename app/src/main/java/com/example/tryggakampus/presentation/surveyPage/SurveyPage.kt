package com.example.tryggakampus.presentation.surveyPage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tryggakampus.data.SurveyQuestions

@Composable
fun SurveyPage(title: String) {
    val questions = SurveyQuestions.questions
    var answers = remember { mutableStateListOf(*Array(questions.size) { "" }) }
    //still need val in order to store the questions

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp))
    {
        item {
            Text(text = title)
            Spacer(modifier = Modifier.height(16.dp))
        }

        //this is to get both the question and the index
        itemsIndexed(questions) { index, question ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth())
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()) {

                    Text(text = "Question ${index + 1}: $question")
                    TextField(
                        value = answers[index],
                        onValueChange = { answers[index] = it },
                        label = { Text("Your Answer") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    //button logic here for storing info in dB once its setup
                }
            ) {
                Text("Submit Answers")
            }
        }
    }
}
