package com.example.kotlin_lab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
                title = { Text("Modern Calculator") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFECF2FF), Color(0xFFE3E3E3))
                    )
                )
                .padding(padding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Result Display
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            shadowElevation = 8f
                            shape = RoundedCornerShape(24.dp)
                            clip = true
                        }
                        .background(Color.White.copy(alpha = 0.2f))
                        .blur(16.dp)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = expression, fontSize = 24.sp, color = Color.DarkGray, maxLines = 1)
                    Text(
                        text = result,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Button Grid
                Column(modifier = Modifier.fillMaxWidth()) {
                    buttons.forEach { row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            row.forEach { label ->
                                ModernCalcButton(
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
                                                    result = "Error"
                                                }
                                            }
                                            "±" -> {
                                                if (expression.startsWith("-")) {
                                                    expression = expression.removePrefix("-")
                                                } else {
                                                    expression = "-$expression"
                                                }
                                            }
                                            else -> expression += label
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
}

@Composable
fun ModernCalcButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val isOperator = label in listOf("AC", "⌫", "%", "÷", "×", "−", "+", "=")
    val bgColor = if (isOperator) Color(0xFF7D9EFF) else Color.White
    val textColor = if (isOperator) Color.White else Color.Black

    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(text = label, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    }
}
