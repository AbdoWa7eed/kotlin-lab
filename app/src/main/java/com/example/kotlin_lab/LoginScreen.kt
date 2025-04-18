package com.example.kotlin_lab

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                title = { Text("Login Activity") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Previous Logins Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("👤 Previous Logins", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    val emailsToShow = if (showAll) allEmails else allEmails.take(3)

                    emailsToShow.forEach {
                        Text("• $it", style = MaterialTheme.typography.bodyMedium)
                    }

                    if (allEmails.size > 3) {
                        TextButton(
                            onClick = { showAll = !showAll },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(if (showAll) "Show Less" else "See All")
                        }
                    }
                }
            }

            // Login Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🔐 Login", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFA6))
                    ) {
                        Text("Login", color = Color.White)
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
