package com.example.tryggakampus.presentation.landingPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tryggakampus.presentation.component.PageContainer


@Composable
fun LandingPage(title: String) {
    PageContainer {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .background(Color.White)
                    .fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Row {
                Text(
                    text = "title",
                    modifier = Modifier,
                    color = Color.hsl(
                        hue = 330f,
                        saturation = 0.75f,
                        lightness = 0.7f
                    ),
                )
                Spacer(
                    modifier = Modifier
                        .padding(16.dp)
                )
                Text(
                    text = "Lorem epsom",
                    modifier = Modifier,
                    color = Color.White,
                )
            }
        }
    }
}
