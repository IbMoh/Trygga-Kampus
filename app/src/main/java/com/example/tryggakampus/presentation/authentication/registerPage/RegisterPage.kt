package com.example.tryggakampus.presentation.authentication.registerPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tryggakampus.LocalNavController
import com.example.tryggakampus.Routes

@Composable
fun RegisterPage() {
    val vm: RegisterViewModel = viewModel<RegisterViewModel>()
    val navController = LocalNavController.current

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign Up")

        OutlinedInput(
            label = "email",
            value = vm.email,
            onValueChange = { vm.onEmailChange(it) },
            isError = !vm.emailIsValid
        )

        OutlinedInput(
            label = "password",
            value = vm.password,
            onValueChange = { vm.onPasswordChange(it) },
            isError = !vm.passwordIsValid
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { vm.onRequestSignUp() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            enabled = (vm.emailIsValid && vm.passwordIsValid)
        ) {
            Text("Sign Up")
        }

        vm.error?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { vm.clearError() }
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = it.message, color = MaterialTheme.colorScheme.onErrorContainer)
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already registered?")
            Button(onClick = { navController.navigate(Routes.Authentication.LoginPage) }) {
                Text(text = "Sign In", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun OutlinedInput(
    label: String,
    value: String = "",
    onValueChange: (v: String) -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        value = value,
        onValueChange = onValueChange,
        isError = isError,
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
}