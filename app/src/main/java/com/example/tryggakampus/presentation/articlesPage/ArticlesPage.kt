package com.example.tryggakampus.presentation.articlesPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tryggakampus.domain.model.ArticleModel

@Composable
fun ArticlesPage(viewModel: ArticlesPageViewModel = viewModel()) {
    val localContext = LocalContext.current
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadArticles(localContext)
    }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(viewModel.articles) { item: ArticleModel ->
                        ArticleBox(
                            article = item,
                            onDelete = { viewModel.deleteArticle(item) },
                            onClick = {},
                            deleteMode = viewModel.deleteMode
                        )
                    }
                }

                if (viewModel.deleteMode) {
                    FloatingActionButton(
                        onClick = { viewModel.toggleDeleteMode() },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Cancel Delete Mode")
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        FloatingActionButton(
                            onClick = { showAddDialog = true },
                            modifier = Modifier.background(Color.Transparent)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Add Article")
                        }

                        FloatingActionButton(
                            onClick = { viewModel.toggleDeleteMode() },
                            modifier = Modifier.background(Color.Transparent)
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = "Enable Delete Mode")
                        }
                    }
                }
            }

            if (showAddDialog) {
                AddArticleDialog(
                    onDismiss = { showAddDialog = false },
                    viewModel = viewModel
                )
            }
        }
    )
}



@Composable
fun ArticleBox(
    article: ArticleModel,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    deleteMode: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ArticleBoxHeader(article.title ?: "")
            ArticleBoxBody(article.summary, article.webpage ?: "No Link")
        }


        if (deleteMode) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete Article", tint = Color.Red)
            }
        }
    }
}

@Composable
fun ArticleBoxHeader(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ArticleBoxBody(content: String, webpage: String) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(content, fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary)
        Text(
            buildAnnotatedString {
                append("Read More")
            },
            color = Color(0xFFF19107),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

