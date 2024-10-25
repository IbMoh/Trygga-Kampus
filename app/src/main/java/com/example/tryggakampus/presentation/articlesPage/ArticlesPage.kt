package com.example.tryggakampus.presentation.articlesPage

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tryggakampus.domain.model.ArticleModel

@Composable
fun ArticlesPage(viewModel: ArticlesPageViewModel = viewModel<ArticlesPageViewModel>()) {
    val localContext = LocalContext.current
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadArticles(localContext)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                Icon(imageVector = Icons.Filled.Add,
                    contentDescription = "Add Article",
                    )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        if (showAddDialog) {
            AddArticleDialog(
                onDismiss = { showAddDialog = false },
                viewModel = viewModel
            )
        }

        LazyColumn(
            modifier = Modifier.padding(paddingValues).padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(viewModel.articles) { item: ArticleModel ->
                ArticleBox(item, onClick = {})
            }
        }
    }
}



@Composable
fun ArticleBox(article: ArticleModel, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
            .padding(10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
         ArticleBoxHeader(article.title?: "")
         ArticleBoxBody(article.summary, article.webpage?: "No Link")
    }
}

@Composable
fun ArticleBoxHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ArticleBoxBody(content: String, webpage: String) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(content, fontSize = 16.sp)

        ClickableText(
            text = AnnotatedString("Read More"),
            style = TextStyle(
                color = Color(0xFFF19107),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webpage))
                context.startActivity(intent)
            },
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun AddArticleDialog(onDismiss: () -> Unit, viewModel: ArticlesPageViewModel) {
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var webpage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Article") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = summary,
                    onValueChange = { summary = it },
                    label = { Text("Summary") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = webpage,
                    onValueChange = { webpage = it },
                    label = { Text("Webpage URL") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.addArticle(title, summary, webpage)
                    onDismiss()
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.border(
                    BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(50)
                )
            ) {
                Text(text="Cancel",
                    color = MaterialTheme.colorScheme.primary,
                    )
            }
        }
    )
}


