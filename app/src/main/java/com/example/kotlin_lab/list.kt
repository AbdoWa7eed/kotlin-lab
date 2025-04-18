package com.example.kotlin_lab

fun main() {

    print("🔢 Enter the size of the list: ")
    var listSize = readlnOrNull()?.toIntOrNull()
    while (listSize == null || listSize <= 0) {
        println("❌ Invalid input. Please enter a valid positive integer.")
        print("🔢 Enter the size of the list: ")
        listSize = readlnOrNull()?.toIntOrNull()
    }

    val list = mutableListOf<Int>()
    enterElements(listSize, list)

    println("\n✅ The original elements are: $list")
    val oddList = list.filter { e -> e % 2 == 1 }.toMutableList()
    println("🎯 The odd elements are: $oddList")
}

private fun enterElements(listSize: Int, list: MutableList<Int>) {
    println("\n📥 Enter the elements one by one:")
    for (i in 1..listSize) {
        print("➡ Enter element $i: ")
        var element = readlnOrNull()?.toIntOrNull()
        while (element == null) {
            print("❌ Invalid input. Please enter an integer for element $i: ")
            element = readlnOrNull()?.toIntOrNull()
        }
        list.add(element)
    }
}
