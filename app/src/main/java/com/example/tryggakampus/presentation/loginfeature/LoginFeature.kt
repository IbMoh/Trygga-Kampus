package com.example.tryggakampus.presentation.loginfeature

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// ViewModel to manage login logic and state
class LoginViewModel : ViewModel() {
    // State variables for username and password
    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    // Function to update username
    fun onUsernameChange(newUsername: String) {
        username = newUsername
    }

    // Function to update password
    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    // Function to handle login action
    fun onLogin(isStudent: Boolean) {
        // Check the user type and perform login logic here
        if (isStudent) {
            println("Logging in as Student with Username: $username, Password: $password")
        } else {
            println("Logging in as Teacher with Username: $username, Password: $password")
        }
    }
}

// Main composable function for the LoginFeature
@Composable
fun LoginFeatureScreen(loginViewModel: LoginViewModel = viewModel()) {
    // Boolean state to toggle between Student and Teacher login
    var isStudentLogin by remember { mutableStateOf(true) }

    // Set up the column layout for the feature
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Buttons to switch between Student and Teacher login views
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { isStudentLogin = true }) {
                Text("Student Login")
            }
            Button(onClick = { isStudentLogin = false }) {
                Text("Teacher Login")
            }
        }

        // Display the login fields based on the login type selected
        LoginFields(
            title = if (isStudentLogin) "Student Login" else "Teacher Login",
            username = loginViewModel.username,
            onUsernameChange = loginViewModel::onUsernameChange,
            password = loginViewModel.password,
            onPasswordChange = loginViewModel::onPasswordChange,
            onLogin = { loginViewModel.onLogin(isStudentLogin) }
        )
    }
}

// Reusable composable for input fields and login button
@Composable
fun LoginFields(
    title: String,
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit
) {
    // Set up the layout for input fields and login button
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.height(16.dp))

        // Username TextField
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange, // Updates the username in the ViewModel
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password TextField with hidden characters
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange, // Updates the password in the ViewModel
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Login button triggers the onLogin function from ViewModel
        Button(onClick = onLogin) {
            Text("Login")
        }
    }
}
