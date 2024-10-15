package com.example.tryggakampus.presentation.articlesPage

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.tryggakampus.domain.repository.ArticlesPageRepository
import com.example.tryggakampus.presentation.component.PageContainer

@Composable
fun ArticlesPage(
    title: String,
    viewModel: ArticlesPageViewModel = viewModel(factory = PdfViewModelFactory(ArticlesPageRepository()))
) {
    PageContainer {
        Text(text = title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        DisplayAllPdfs(viewModel)
    }
}

@Composable
fun DisplayAllPdfs(viewModel: ArticlesPageViewModel) {
    val context = LocalContext.current
    val pdfUrls by viewModel.pdfUrls.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (pdfUrls.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Available PDFs", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            pdfUrls.forEachIndexed { index, pdfUrl ->
                Button(
                    onClick = {
                        // Use Intent to Open the PDF in an external app.
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf")
                        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(text = "Article ${index + 1}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    } else {
        Text("No PDFs found.", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))
    }
}





