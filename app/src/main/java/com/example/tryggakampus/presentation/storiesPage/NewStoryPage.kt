package com.example.tryggakampus.presentation.storiesPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryggakampus.R
import com.example.tryggakampus.data.Config
import com.example.tryggakampus.presentation.component.PageContainer

@Composable
fun NewStoryPage(modifier: Modifier, viewModel: StoriesPageViewModel) {
    PageContainer(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(stringResource(R.string.stories_title), fontSize = 20.sp)
        Column (modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = viewModel.storyFormValue.value,
                label = { Text(stringResource(R.string.stories_input_label)) },
                onValueChange = { viewModel.setStoryFormValue(it) },
                minLines = 5,
                maxLines = 8
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val textLength = viewModel.storyFormValue.value.text.length

                val color =
                    if (textLength < Config.Stories.minLength
                    || textLength > Config.Stories.maxLength)
                        Color.Red
                    else
                        Color.Unspecified

                SwitchWithIconExample(stringResource(R.string.stories_anonymous_label))

                if (textLength < Config.Stories.minLength) {
                    Text("Minimum $textLength / ${Config.Stories.minLength}", color = color)
                } else {
                    Text("Maximum $textLength / ${Config.Stories.maxLength}", color = color)
                }
            }
        }
    }
}

@Composable
fun SwitchWithIconExample(label: String) {
    var checked by remember { mutableStateOf(true) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            thumbContent = {
                Icon(
                    imageVector = if (checked) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        )
    }
}