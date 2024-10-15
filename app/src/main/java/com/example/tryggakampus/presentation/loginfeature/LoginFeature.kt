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
import androidx.compose.ui.tooling.preview.Preview

// ViewModel to manage login logic and state
class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onUsernameChange(newUsername: String) {
        username = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onLogin(isStudent: Boolean) {
        // Login logic can be implemented here
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
    var isStudentLogin by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogin) {
            Text("Login")
        }
    }
}

// Preview function to visualize the LoginFeature
@Preview(showBackground = true)
@Composable
fun PreviewLoginFeature() {
    LoginFeatureScreen()
}
