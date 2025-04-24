package com.example.kotlin_lab

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val sharedPref = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    val emailListKey = "emailList"

    var allEmails by remember {
        mutableStateOf(
            sharedPref.getStringSet(emailListKey, setOf())?.toList()?.reversed() ?: emptyList()
        )
    }
    var showAll by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Back to Labs", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF262A56))
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFEEF2FF), Color(0xFFD1C4E9))))
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text("🔐 Secure Login", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF3E3E3E))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                Toast.makeText(context, "Please fill in both fields", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            handleLogin(context, email, password)

                            val currentSet = sharedPref.getStringSet(emailListKey, setOf())?.toMutableSet() ?: mutableSetOf()

                            if (!currentSet.contains(email)) {
                                currentSet.add(email)
                                sharedPref.edit { putStringSet(emailListKey, currentSet) }
                                allEmails = currentSet.toList().reversed()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E35B1))
                    ) {
                        Text("Log In", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
                    }

6

                    if (allEmails.isNotEmpty()) {
                        Divider(color = Color.LightGray, thickness = 1.dp)
                        Text("Previous Logins", style = MaterialTheme.typography.titleMedium)

                        val shown = if (showAll) allEmails else allEmails.take(3)
                        shown.forEach { emailEntry ->
                            Text("• $emailEntry", style = MaterialTheme.typography.bodySmall)
                        }

                        if (allEmails.size > 3) {
                            TextButton(onClick = { showAll = !showAll }) {
                                Text(if (showAll) "Show Less" else "See All", color = Color(0xFF5E35B1))
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun handleLogin(context: Context, email: String, password: String) {
    val sharedPref = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    val savedPassword = sharedPref.getString(email, null)

    if (savedPassword == null) {
        sharedPref.edit {
            putString(email, password)
        }
        Toast.makeText(context, "First time login saved!", Toast.LENGTH_SHORT).show()
        return
    }

    if (savedPassword == password) {
        Toast.makeText(context, "Welcome back! You've logged in before.", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "Wrong Password! Please Try Again", Toast.LENGTH_LONG).show()
    }
}
