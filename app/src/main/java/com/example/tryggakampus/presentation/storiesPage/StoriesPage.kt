package com.example.tryggakampus.presentation.storiesPage

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tryggakampus.LocalNavController
import com.example.tryggakampus.Routes
import com.example.tryggakampus.domain.model.StoryModel
import kotlin.math.roundToInt

@Composable
fun StoriesPage(viewModel: StoriesPageViewModel = viewModel<StoriesPageViewModel>()) {
    val localContext = LocalContext.current
    val navigator = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.loadStories(localContext)
    }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue = remember { derivedStateOf { (screenWidth.value).dp } }

    val animatedOffset by animateDpAsState(
        targetValue =
            if (!viewModel.showNewStoryForm.value)
                offsetValue.value
            else
                0.dp
        ,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "Animated Offset"
    )

    Box(modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(viewModel.stories) { item: StoryModel ->
                StoryBox(item, short = true, onClick = {
                    navigator.navigate(Routes.StoriesNavGraph.StoryPage(item.id!!))
                })
            }
        }

        NewStoryPage(
            viewModel = viewModel,
            modifier = Modifier.offset(x = animatedOffset)
        )
    }
}

@Composable
fun StoryBox(story: StoryModel, short: Boolean? = false, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        StoryBoxHeader(story.title?: "", story.author?: "Anonymous")
        StoryBoxBody(
            if (story.content.length > 200 && short == true)
                story.content.substring(0, 200) + "..."
            else
                story.content
        )
    }
}

@Composable
fun StoryBoxHeader(title: String, author: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text(author, fontSize = 12.sp)
    }
}

@Composable
fun StoryBoxBody(content: String) {
    Text(content, fontSize = 16.sp)
}