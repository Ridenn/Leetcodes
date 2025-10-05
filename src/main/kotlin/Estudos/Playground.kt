package study

fun main() {
    val array = listOf(1, 2, 3, 4, 5).filter { it % 2 == 0 }.map { it * 2 }.sorted()
    println(array)

    println(twoSum(intArrayOf(2,7,11,15), 9).toUIntArray())
}

fun twoSum(nums: IntArray, target: Int): IntArray {
    nums.forEachIndexed { indexFir, first ->
        nums.forEachIndexed { indexSec, second ->
            if (indexFir == indexSec) return@forEachIndexed
            if (target - first == second) return intArrayOf(indexFir, indexSec)
        }
    }
    return intArrayOf()
}