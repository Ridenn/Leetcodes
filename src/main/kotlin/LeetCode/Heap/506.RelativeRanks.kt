package LeetCode.Heap

import java.util.*
import kotlin.math.sqrt

fun main() {
    println(findRelativeRanks(intArrayOf(10,3,8,9,4)).contentToString())
//    println(findRelativeRanks(intArrayOf(5,4,3,2,1)).contentToString())
//    println(findRelativeRanks(intArrayOf(6,7,8,9,10)).contentToString())
//    println(findRelativeRanks(intArrayOf(7,8,10,5,6)).contentToString())
}

fun findRelativeRanks(scores: IntArray): Array<String> {
    val maxHeap = PriorityQueue<Int>(compareByDescending { it })

    // Add to heap + to map, with value as key and empty as value
    // hint: the value is gonna be added later
    for (score in scores) {
        maxHeap.offer(score)
    }

    // Loops through score indices, getting the ordered top of the heap,
    // translating it and adding the text to the map, where the key is the value
    var count = 0
    val scoreMap = mutableMapOf<Int, String>()
    while (count < scores.size) {
        val podiumTranslation = when (count) {
            0 -> "Gold Medal"
            1 -> "Silver Medal"
            2 -> "Bronze Medal"
            else -> (count + 1).toString()
        }

        val higherScore = maxHeap.poll()
        scoreMap[higherScore] = podiumTranslation
        count++
    }

    // Loop again through scores, getting the translated scores and
    // adding them in order inside a new arrayList
    // hint: convert the arrayList to typedArray (Array<String>) before returning
    val newList = arrayListOf<String>()
    for (score in scores) {
        val medal = scoreMap.getValue(score)
        newList.add(medal)
    }
    return newList.toTypedArray()
}
