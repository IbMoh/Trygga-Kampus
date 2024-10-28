package com.example.tryggakampus.presentation.authentication.loginPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.tryggakampus.LocalNavController
import com.example.tryggakampus.Routes

@Composable
fun LoginPage() {
    val loginViewModel: LoginViewModel = viewModel<LoginViewModel>()
    var isStudentLogin by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loginViewModel.signingIn) {
            LoadingIndicator()
            return
        }

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

            email = loginViewModel.email,
            onEmailChange = loginViewModel::onEmailChange,
            emailIsValid = loginViewModel.emailIsValid,

            password = loginViewModel.password,
            onPasswordChange = loginViewModel::onPasswordChange,
            passwordIsValid = loginViewModel.passwordIsValid,

            onRequestLogin = { loginViewModel.onRequestLogin() }
        )

        Spacer(modifier = Modifier.height(16.dp))
        loginViewModel.error?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { loginViewModel.clearError() }
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = it.message, color = MaterialTheme.colorScheme.onErrorContainer)
            }
        }
    }
}

@Composable
fun LoginFields(
    title: String,
    email: String,
    onEmailChange: (String) -> Unit,
    emailIsValid: Boolean,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordIsValid: Boolean,
    onRequestLogin: () -> Unit
) {
    val navController = LocalNavController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("email@company.xyz") },
            modifier = Modifier.fillMaxWidth(),
            isError = !emailIsValid,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,

                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,

                cursorColor = MaterialTheme.colorScheme.secondary,

                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.secondary,
                    backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                )
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = !passwordIsValid,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,

                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,

                cursorColor = MaterialTheme.colorScheme.secondary,

                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.secondary,
                    backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                )
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRequestLogin,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            enabled = (emailIsValid && passwordIsValid)
        ) {
            Text("Sign In")
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Need an account?")
            Button(onClick = { navController.navigate(Routes.Authentication.RegisterPage) }) {
                Text(text = "Sign Up", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Signing in, please wait ...")
        Spacer(modifier = Modifier.size(20.dp))
        CircularProgressIndicator(
            modifier = Modifier.width(32.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginFeature() {
    LoginPage()
}
