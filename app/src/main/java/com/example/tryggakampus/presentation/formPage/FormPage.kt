package com.example.tryggakampus.presentation.formPage

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryggakampus.R
import com.example.tryggakampus.presentation.component.PageContainer

@Composable
fun FormPage(title: String) {

    val name = remember { mutableStateOf(TextFieldValue()) }
    val subject = remember { mutableStateOf(TextFieldValue()) }
    val message = remember { mutableStateOf(TextFieldValue()) }

    PageContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.helping_hands1),
                contentDescription = "Logo"
            )
            FormsFields(
                name,
                subject,
                message
            )
            SubmitButton(
                name.value.text,
                subject.value.text,
                message.value.text
            )
        }
    }
}

@Composable
fun FormsFields(
    name: MutableState<TextFieldValue>,subject: MutableState<TextFieldValue>,message: MutableState<TextFieldValue>
){
    TextField(
        value = name.value,
        onValueChange = { name.value = it },
        placeholder = { Text(text = "Enter Your Name Here") },
        modifier = Modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp)),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(10.dp))

    TextField(
        value = subject.value,
        onValueChange = { subject.value = it },
        placeholder = { Text(text = "Subject Field") },
        modifier = Modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
    )

    Spacer(modifier = Modifier.height(10.dp))

    TextField(
        value = message.value,
        onValueChange = { message.value = it },
        placeholder = { Text(text = "Enter Your Message Here") },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(200.dp),
        singleLine = false
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun SubmitButton(
    name: String,
    subject: String,
    message: String
) {
    val ctx = LocalContext.current

    Button(
        onClick = {
            if (subject.isEmpty() || message.isEmpty()) {
                // Show a toast if required fields are missing
                Toast.makeText(ctx, "Please fill out all fields except for Name.", Toast.LENGTH_SHORT).show()
            } else {
                val recipientEmail = "ibrx2002forwork@gmail.com"
                val senderName = if (name.isEmpty()) "Anonymous" else name
                val emailBody = "Hello, my name is $senderName.\n\n$message\n\nThank you!"
                val intent = Intent(Intent.ACTION_SEND)

                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, emailBody)
                intent.type = "message/rfc822"

                ctx.startActivity(Intent.createChooser(intent, "Choose an Email client : "))
            }
        },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(40.dp))
            .size(width = 200.dp, height = 60.dp)
            .background(Color.Red),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        )
    ) {
        Text(
            "Submit",
            fontSize = 20.sp
        )
    }
}