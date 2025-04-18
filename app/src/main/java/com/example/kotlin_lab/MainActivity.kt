package com.example.kotlin_lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kotlin_lab.ui.theme.KotlinlabTheme
import androidx.navigation.compose.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinlabTheme {
                AppNavigator()
            }
        }
    }
}
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("calculator") { CalculatorScreen(navController) }
        composable("login") { LoginScreen(navController) }

    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Welcome to Kotlin Labs",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF2E2E2E),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Surface(
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 8.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Choose Your Lab",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF6842FF)),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    CustomButton(
                        label = "Lab 1 (Calculator)",
                        onClick = { navController.navigate("calculator") },
                        backgroundColor = Color(0xFF6842FF)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButton(
                        label = "Lab 2 (Login)",
                        onClick = { navController.navigate("login") },
                        backgroundColor = Color(0xFF42A5F5)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    label: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF6842FF)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

