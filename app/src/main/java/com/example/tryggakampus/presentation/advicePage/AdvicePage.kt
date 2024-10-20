package com.example.tryggakampus.presentation.advicePage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tryggakampus.R


import com.example.tryggakampus.domain.model.AdviceItem
import java.util.Locale.Category

// idea is to have a "landing page" for the advice where you can pick in between 2 categories.
// you can then go back after selecting. The advice will be displayed by categories.
@Composable
fun AdvicePage() {
    CategorySelectionScreen()
}

@Composable
fun CategorySelectionScreen() {
    Column(

    ) {
        Text(
            text = "Select Category",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )

        CategoryCard(
            title = stringResource(id = R.string.preventive_advice_category_title),
        )

        CategoryCard(
            title = stringResource(id = R.string.support_advice_category_title),
        )


    }
}


