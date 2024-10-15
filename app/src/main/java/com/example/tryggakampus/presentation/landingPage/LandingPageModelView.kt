package com.example.tryggakampus.presentation.landingPage

import androidx.lifecycle.ViewModel
import com.example.tryggakampus.R

class LandingPageModelView: ViewModel() {

    val title: String = "Welcome to Trygga Kampus!"
    val aboutUs: String = "About Us!"
    val info1: String = "Here will be info regarding the app like what we aim to do and how it functions"
    val button: String = "" +
            "If you need serous help, please Get in touch with either you University counselor or one of our " +
            "professionals using this button."
    val buttonInfo: String = "This button will prompt you with a forum that you fill in with what you're dealing with" +
            " and send it to someone to provide the help needed."
    val image = R.drawable.logo_2

}