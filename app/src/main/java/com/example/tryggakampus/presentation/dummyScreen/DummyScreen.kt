package com.example.tryggakampus.presentation.dummyScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DummyScreen(title: String) {
    Column (modifier = Modifier.fillMaxWidth()) {
        Text(title)
    }
}
