package com.example.tryggakampus.presentation.loginfeature

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var loginResult by mutableStateOf("")
        private set

    private val db = FirebaseFirestore.getInstance()

    fun onUsernameChange(newUsername: String) {
        username = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    suspend fun onLogin(isStudent: Boolean) {

        val collection = db.collection("Login")
        try {
            val query = collection
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .await()

            if (!query.isEmpty) {
                loginResult = if (isStudent) {
                    "Logged in as Student"
                } else {
                    "Logged in as Teacher"
                }
            } else {
                loginResult = "Login failed. Check your credentials."
            }
        } catch (e: Exception) {
            loginResult = "Error: ${e.message}"
        }
    }
}
