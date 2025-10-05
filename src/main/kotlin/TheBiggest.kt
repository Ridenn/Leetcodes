package leetcodes

fun main() {
    val inputs = readLine()?.split(" ")
    val numbers = inputs?.map { it.toInt() }
    var biggest = -1

    numbers?.forEach { num ->
        biggest = if(num > biggest) num else biggest
    }

    println("$biggest eh o maior")
}