package com.example.tryggakampus.presentation.articlesPage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddArticleDialog(onDismiss: () -> Unit, viewModel: ArticlesPageViewModel) {
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var webpage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Article") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        showError = false
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && title.isBlank()
                )
                OutlinedTextField(
                    value = summary,
                    onValueChange = {
                        summary = it
                        showError = false
                    },
                    label = { Text("Summary") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && summary.isBlank()
                )
                OutlinedTextField(
                    value = webpage,
                    onValueChange = {
                        webpage = it
                        showError = false
                    },
                    label = { Text("Webpage URL") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && webpage.isBlank()
                )


                if (showError) {
                    Text(
                        text = "All fields are required.",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && summary.isNotBlank() && webpage.isNotBlank()) {
                        viewModel.addArticle(title, summary, webpage)
                        onDismiss()
                    } else {
                        showError = true
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.border(
                    BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(50)
                )
            ) {
                Text("Cancel", color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}
