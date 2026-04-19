package leetcodes.LeetCode

fun main() {
    println(twoSumNew(intArrayOf(2,7,11,15), 9).contentToString()) // [0, 1]
    println(twoSumNew(intArrayOf(3,2,4), 6).contentToString()) // [1, 2]
    println(twoSumNew(intArrayOf(3,3), 6).contentToString()) // [0, 1]
}

fun twoSum(nums: IntArray, target: Int): IntArray {
    for(firstIndex in nums.indices /* in 0 until nums.size */) {
        for (secondIndex in (firstIndex + 1) until nums.size) {
            val sum = nums[firstIndex] + nums[secondIndex]
            if (sum == target) {
                return intArrayOf(firstIndex, secondIndex)
            }
        }
    }
    return intArrayOf()
}

fun twoSumBestSolution(nums: IntArray, target: Int): IntArray {
    var left = 0
    var right = nums.size - 1
    val map = mutableMapOf<Int, Int>()
    
    while (left <= right) {
        val leftNum = nums[left]
        map[leftNum]?.let { return intArrayOf(it, left) }
        val rightNum = nums[right]
        if (leftNum + rightNum == target) {
            return intArrayOf(left, right)
        }
        map[rightNum]?.let { return intArrayOf(it, right) }
        map.put(target - leftNum, left)
        map.put(target - rightNum, right)
        left++
        right--
    }
    throw IllegalArgumentException()
}

/*
    * Time complexity: O(n) -> traverses the array only once
    * Space complexity: O(n) -> stores at most n numbers and their indices in the map
    * Approach: First we create a map of numbers to their indices. For each number,
        we calculate which value we need to complete the target sum. Then we check
        if this needed value was already seen before. If it was, we return the index
        stored in the map and the current index.
        If it was not seen yet, we store the current number and its index so a future
        number can use it as its complement.
    * Intuition:
        - Hash map
        - Store previous numbers as (number -> index)
        - For each current number, search for target - current number
        - If the complement exists in the map, we found the pair
*/
fun twoSumNew(nums: IntArray, target: Int): IntArray {
    val map = mutableMapOf<Int, Int>()

    for (index in nums.indices) { // O(n)
        val lookup = target - nums[index]

        // If we found the lookup -> return lookup index + current index
        map[lookup]?.let { return intArrayOf(it, index) } // O(1)

        // number -> index position
        map[nums[index]] = index
    }
    return intArrayOf()
}
