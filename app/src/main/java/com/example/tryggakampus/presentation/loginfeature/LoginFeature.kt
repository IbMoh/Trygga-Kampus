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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
fun LoginFeature() {
    val loginViewModel: LoginViewModel = viewModel<LoginViewModel>()
    val coroutineScope = rememberCoroutineScope()
    var isStudentLogin by remember { mutableStateOf(true) }
    var loginResult by remember { mutableStateOf("") }

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
            onLogin = {
                coroutineScope.launch {
                    loginViewModel.onLogin(isStudentLogin)
                    loginResult = loginViewModel.loginResult
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = loginResult)
    }
}

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


@Preview(showBackground = true)
@Composable
fun PreviewLoginFeature() {
    LoginFeature()
}
