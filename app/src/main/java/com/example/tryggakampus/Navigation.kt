package com.example.tryggakampus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tryggakampus.presentation.landingPage.LandingPage
import com.example.tryggakampus.presentation.profilePage.ProfilePage
import com.example.tryggakampus.presentation.settingsPage.SettingsPage
import kotlinx.serialization.Serializable

// Define a Composition Local for NavController
val LocalNavController = compositionLocalOf<NavHostController> {
    error("NavController not provided")
}

sealed interface Routes {
    @Serializable data class LandingPage(val title: String = "Home"): Routes
    @Serializable data class SettingsPage(val title: String = "Settings"): Routes
    @Serializable data class ProfilePage(val title: String = "Profile"): Routes
}

@Composable
fun Navigation(
    children: @Composable() (page: @Composable()() -> Unit) -> Unit
) {
    val navController = rememberNavController()

    /*
    *   The reason for adding CompositionLocalProvider is to avoid prop-drilling,
    *   which is a problem with frameworks like react(, and jetpack compose)
    *   https://www.freecodecamp.org/news/prop-drilling-in-react-explained-with-examples/
    *
    *   solution from:
    *   https://medium.com/@ramadan123sayed/composition-local-in-jetpack-compose-4d0a54afa67c#36f0
    * */
    CompositionLocalProvider(LocalNavController provides navController) {
        children {
            NavHost(navController = navController, startDestination = Routes.LandingPage()) {
                composable<Routes.LandingPage> {
                    val args = it.toRoute<Routes.LandingPage>()
                    LandingPage(args.title)
                }

                composable<Routes.ProfilePage> {
                    val args = it.toRoute<Routes.ProfilePage>()
                    ProfilePage(args.title)
                }

                composable<Routes.SettingsPage> {
                    val args = it.toRoute<Routes.SettingsPage>()
                    SettingsPage(args.title)
                }
            }
        }
    }
}