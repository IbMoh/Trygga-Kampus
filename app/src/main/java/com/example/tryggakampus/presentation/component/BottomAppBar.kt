package com.example.tryggakampus.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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
        Routes.SettingsPage().routeName() -> BottomAppBar { BottomSettingsBar() }
        Routes.StoriesPage().routeName() -> BottomAppBar { BottomStoriesBar() }
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

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = { vm.setTabIndex(ArticleTabs.TAB_ONE) }) {
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

@Composable
fun BottomStoriesBar() {
    val navController = LocalNavController.current

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val navigationGraphEntry = remember(currentBackStackEntry) {
        navController.getBackStackEntry(Routes.StoriesPage())
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

        IconButton(
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
