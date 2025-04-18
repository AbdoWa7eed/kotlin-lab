package com.example.kotlin_lab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.objecthunter.exp4j.ExpressionBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(navController: NavController) {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val buttons = listOf(
        listOf("AC", "⌫", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "−"),
        listOf("1", "2", "3", "+"),
        listOf("±", "0", ".", "=")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculator Activity") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = expression,
                    fontSize = 24.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
                Text(
                    text = result,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                buttons.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        row.forEach { label ->
                            CalculatorButton(
                                label = label,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    when (label) {
                                        "AC" -> {
                                            expression = ""
                                            result = ""
                                        }
                                        "⌫" -> {
                                            if (expression.isNotEmpty())
                                                expression = expression.dropLast(1)
                                        }
                                        "=" -> {
                                            try {
                                                val replaced = expression
                                                    .replace("÷", "/")
                                                    .replace("×", "*")
                                                    .replace("−", "-")
                                                val eval = ExpressionBuilder(replaced).build().evaluate()
                                                result = eval.toString()
                                            } catch (e: Exception) {
                                                result = "Error : ${e.message}"
                                            }
                                        }
                                        "±" -> {
                                            if (expression.startsWith("-")) {
                                                expression = expression.removePrefix("-")
                                            } else {
                                                expression = "-$expression"
                                            }
                                        }
                                        else -> {
                                            expression += label
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (label) {
                "AC", "⌫", "%", "÷", "×", "−", "+" -> Color(0xFF00BFA6)
                "=" -> Color(0xFF00BFA6)
                else -> Color(0xFFE0E0E0)
            },
            contentColor = Color.Black
        )
    ) {
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
