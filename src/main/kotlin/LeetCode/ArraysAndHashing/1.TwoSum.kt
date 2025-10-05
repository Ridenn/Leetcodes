package leetcodes.LeetCode

fun main() {
    print(twoSum(intArrayOf(2,7,11,15), 9).contentToString())
    print(twoSum(intArrayOf(3,2,4), 6).contentToString())
    print(twoSum(intArrayOf(3,3), 6).contentToString())
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
