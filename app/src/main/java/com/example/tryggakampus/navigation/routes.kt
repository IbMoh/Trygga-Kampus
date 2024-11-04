package com.example.tryggakampus.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    fun routeName(): String

    @Serializable
    data class LandingPage(val title: String = "Home"): Routes {
        override fun routeName() = "LandingPage"
    }

    @Serializable
    data class SettingsPage(val title: String = "Settings"): Routes {
        override fun routeName() = "SettingsPage"
    }

    @Serializable
    data class ProfilePage(val title: String = "Profile"): Routes {
        override fun routeName() = "ProfilePage"
    }

    @Serializable
    data class ArticlesPage(val title: String = "Articles"): Routes {
        override fun routeName() = "ArticlesPage"
    }

    @Serializable
    data class FormPage(val title: String = "Form"): Routes {
        override fun routeName() = "FormPage"
    }

    @Serializable
    object StoriesNavGraph {
        @Serializable
        data object StoriesPage: Routes {
            override fun routeName() = "StoriesPage"
        }

        @Serializable
        data class StoryPage(val storyModelId: String = "n07f0und"): Routes {
            override fun routeName() = "StoryPage"
        }
    }

    @Serializable
    data class AdvicePage(val title: String = "Advice"): Routes {
        override fun routeName() = "AdvicePage"
    }

    @Serializable
    data class SurveyPage(val title: String = "Survey"): Routes {
        override fun routeName() = "SurveyPage"
    }

    @Serializable
    data object Authentication {
        @Serializable
        data object LoginPage: Routes {
            override fun routeName() = "LoginPage"
        }

        @Serializable
        data object RegisterPage: Routes {
            override fun routeName() = "RegisterPage"
        }
    }
}