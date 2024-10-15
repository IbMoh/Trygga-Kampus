package com.example.tryggakampus.presentation.component.customDrawer

import com.example.tryggakampus.R


enum class DrawerItem(
    val title: String,
    val icon: Int
) {
    Home(
        icon = R.drawable.baseline_home_filled_24,
        title = "Home"
    ),
    Profile(
        icon = R.drawable.baseline_person_24,
        title = "Profile"
    ),
    Articles(
        icon = R.drawable.baseline_articles_24,
        title = "Articles"
    ),
    Forum(
        icon = R.drawable.baseline_forum_24,
        title = "Forum"
    ),
    Settings(
        icon = R.drawable.baseline_settings_24,
        title = "Settings"
    )
}