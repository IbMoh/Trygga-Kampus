package com.example.tryggakampus.presentation.loginfeature

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

class LoginFeature {
    // Main composable function for the Login Feature
    @Composable
    fun LoginFeatureScreen() {
        // State to manage whether the Student or Teacher login screen is visible
        var isStudentLogin by remember { mutableStateOf(true) }

        // Layout container
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Row for the toggle buttons
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

            // Conditionally show Student or Teacher Login based on state
            if (isStudentLogin) {
                StudentLogin()
            } else {
                TeacherLogin()
            }
        }
    }

    // Composable for Student Login
    @Composable
    fun StudentLogin() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Student Login")
            // Add login fields, e.g., TextFields for Username/Password, here
        }
    }

    // Composable for Teacher Login
    @Composable
    fun TeacherLogin() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Teacher Login")
            // Add login fields, e.g., TextFields for Username/Password, here
        }
    }
}
