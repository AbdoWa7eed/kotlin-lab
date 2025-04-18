package com.example.kotlin_lab
fun main() {
    var n: Int? = null
    while (n == null) {
        print("Enter a number: ")
        n = readlnOrNull()?.toIntOrNull()
    }
    val factorial = factorial(n)
    print(factorial)
}


fun factorial(n: Int): Double {
    return if (n == 1) 1.0 else n * factorial(n - 1)
}