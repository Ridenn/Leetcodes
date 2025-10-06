package LeetCode.Heap

import java.util.*

fun main() {
    println(topKFrequent(arrayOf("i","love","leetcode","i","love","coding"), 2))
//    println(topKFrequent(arrayOf("the","day","is","sunny","the","the","the","sunny","is","is"), 4))
}

fun topKFrequent(words: Array<String>, k: Int): List<String> {
    val freqMap = mutableMapOf<String, Int>()

    for (word in words) {
        freqMap[word] = freqMap.getOrDefault(word, 0) + 1
    }

    val minHeap = PriorityQueue<Pair<Int, String>>() { a, b ->
        if(a.first == b.first) {
            // if words size is equal, compare the word
            b.second.compareTo(a.second)
        } else {
            // else, just compare the sizes
            // if a smaller than b, then negative result -> a comes first in heap
            // if a bigger than b, then positive result -> b comes first in heap
            a.first - b.first
        }
    }

    freqMap.forEach {
        val word = it.key
        val frequency = it.value
        minHeap.offer(frequency to word)
        if (minHeap.size > k) minHeap.poll()
    }

    val resultList = mutableListOf<String>()
    while (minHeap.isNotEmpty()) {
        resultList.add(minHeap.poll().second)
    }
    return resultList.reversed()
}
