package com.example.tryggakampus.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.tryggakampus.LocalNavController
import com.example.tryggakampus.Routes
import com.example.tryggakampus.data.Config
import com.example.tryggakampus.presentation.settingsPage.ArticleTabs
import com.example.tryggakampus.presentation.settingsPage.SettingsPageViewModel
import com.example.tryggakampus.presentation.storiesPage.StoriesPageViewModel

@Composable
fun BottomAppBar() {
    val navigator = LocalNavController.current
    val currentRoute = navigator.currentBackStackEntryAsState().value?.destination?.route
        ?: return

    val className = currentRoute
        .substringBefore("?")
        .substringAfterLast(".")

    when (className) {
        Routes.SettingsPage().routeName() -> BottomSettingsBar()
        Routes.StoriesNavGraph.StoriesPage.routeName() -> BottomStoriesBar()
        // Routes.StoriesNavGraph.StoryPage().routeName() -> BottomAppBar { BottomStoryBar() }
        // Routes.ArticlesPage().routeName() -> BottomAppBar { BottomArticlesBar() }
        // Routes.LandingPage().routeName() -> BottomAppBar { BottomLandingBar() }
        // Routes.ProfilePage().routeName() -> BottomAppBar { BottomProfileBar() }
        else -> {}
    }
}


@Composable
fun BottomSettingsBar() {
    val navController = LocalNavController.current
    val navigationGraphEntry = remember {
        navController.getBackStackEntry<Routes.SettingsPage>()
    }
    val vm = viewModel<SettingsPageViewModel>(navigationGraphEntry)

    BottomAppBar(
        modifier = Modifier
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(10.dp)),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            IconButton(
                onClick = { vm.setTabIndex(ArticleTabs.TAB_ONE) },
                // temporary color for an active state:
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "general info"
                )
            }
            IconButton(onClick = { vm.setTabIndex(ArticleTabs.TAB_TWO) }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "articles about health"
                )
            }
            IconButton(onClick = { vm.setTabIndex(ArticleTabs.TAB_THREE) }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "location"
                )
            }
        }
    }
}

@Composable
fun BottomStoriesBar() {
    val navController = LocalNavController.current

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val navigationGraphEntry = remember(currentBackStackEntry) {
        navController.getBackStackEntry(Routes.StoriesNavGraph.StoriesPage)
    }

    val vm: StoriesPageViewModel = viewModel(navigationGraphEntry)

    BottomAppBar(
        modifier = Modifier
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(10.dp)),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row (
                modifier = Modifier.animateContentSize(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
                        contentColor = MaterialTheme.colorScheme.inverseSurface
                    ),
                    onClick = { vm.setShowNewStoryForm(!vm.showNewStoryForm.value) }
                ) {
                    val text = if (!vm.showNewStoryForm.value) "Add a story" else "Cancel"
                    Icon(
                        imageVector = if (!vm.showNewStoryForm.value) Icons.Default.Add else Icons.Default.Close,
                        contentDescription = text
                    )
                    Text(text)
                }

                AnimatedVisibility(
                    visible = vm.showNewStoryForm.value,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = { vm.submitStory() },
                        enabled = (
                            vm.storyFormValue.value.text.length >= Config.Stories.minLength &&
                            vm.storyFormValue.value.text.length <= Config.Stories.maxLength
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Submit your story"
                        )
                        Text("Submit")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomStoryBar() {
    val navController = LocalNavController.current

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val navigationGraphEntry = remember(currentBackStackEntry) {
        navController.getBackStackEntry(Routes.StoriesNavGraph.StoriesPage)
    }

    val vm: StoriesPageViewModel = viewModel(navigationGraphEntry)

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 50.dp))
                .background(
                    if (vm.showNewStoryForm.value)
                        MaterialTheme.colorScheme.error
                    else
                        Color.Transparent
                ),
            onClick = { vm.setShowNewStoryForm(!vm.showNewStoryForm.value) }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Write a new story"
            )
        }
    }
}

@Composable
fun BottomLandingBar() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "menu"
            )
        }
    }
}

@Composable
fun BottomProfileBar() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "menu"
            )
        }
    }
}
