package com.example.kotlin_lab

fun main() {
    while (true) {
        println("\n🧮 Calculator Menu:")
        println("1️⃣ Add\n2️⃣ Subtract\n3️⃣ Multiply\n4️⃣ Divide")

        var operation: Int? = null
        while (operation == null || operation !in 1..4) {
            print("🔢 Choose an operation (1-4): ")
            operation = readlnOrNull()?.toIntOrNull()
            if (operation !in 1..4) {
                println("❌ Invalid choice! Please select a valid option (1-4).")
            }
        }

        val num1 = getNumber("Enter first number: ")
        val num2 = getNumber("Enter second number: ")

        performOperation(operation, num1, num2)

        print("\n🔄 Do you want to perform another calculation? (y/n): ")
        val answer = readln().trim().lowercase()
        if (answer != "y") {
            println("👋 Exiting calculator. Have a great day! 🚀")
            break
        }
    }
}

private fun getNumber(prompt: String): Double {
    var num: Double? = null
    while (num == null) {
        print("📌 $prompt ")
        num = readlnOrNull()?.toDoubleOrNull()
        if (num == null) {
            println("❌ Invalid input! Please enter a valid number.")
        }
    }
    return num
}

private fun performOperation(operation: Int, num1: Double, num2: Double) {
    val result = when (operation) {
        1 -> add(num1, num2)
        2 -> subtract(num1, num2)
        3 -> multiply(num1, num2)
        4 -> {
            if (num2 == 0.0) {
                println("🚫 Cannot divide by zero!")
                return
            }
            divide(num1, num2)
        }
        else -> throw IllegalArgumentException("Invalid operation")
    }
    val operationSymbol = listOf("+", "-", "×", "÷")[operation - 1]
    println("\n✅ Result: $num1 $operationSymbol $num2 = $result")
}

fun add(a: Double, b: Double) = a + b
fun subtract(a: Double, b: Double) = a - b
fun multiply(a: Double, b: Double) = a * b
fun divide(a: Double, b: Double) = a / b
