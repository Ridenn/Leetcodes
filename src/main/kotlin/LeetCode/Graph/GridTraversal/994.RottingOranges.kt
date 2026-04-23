package LeetCode.Graph.GridTraversal

fun main() {
    println(orangesRotting(arrayOf(
        intArrayOf(2, 1, 1),
        intArrayOf(1, 1, 0),
        intArrayOf(0, 1, 1)
    ))) // 4

    println(orangesRotting(arrayOf(
        intArrayOf(2, 1, 1),
        intArrayOf(0, 1, 1),
        intArrayOf(1, 0, 1)
    ))) // -1

    println(orangesRotting(arrayOf(
        intArrayOf(0, 2)
    ))) // 0
}

/*
* Time complexity: O(m * n * 4 sides) = O(m * n) -> we scan the grid once and each cell is processed at most once in the BFS
* Space complexity: O(m * n) -> in the worst case, it adds the entire m * n grid to the queue
* Approach: First we scan the grid to count all fresh oranges and enqueue all rotten oranges.
*   Then we run a multi-source BFS, where all initially rotten oranges start spreading at the same time.
*   For each minute, we process the current queue size only, meaning all oranges that are rotten in that minute.
*   When a fresh neighbor is found, we turn it rotten, decrement the fresh count, and add it to the queue
*   so it can spread rot in the next minute.
*   At the end, if there are still fresh oranges left, we return -1. Otherwise, we return the number of minutes.
* Intuition:
*   - BFS
*   - enqueue all rotten oranges first
*   - count fresh oranges
*   - process the BFS level by level, counting each 'minute' per level
*   - countdown fresh oranges per rotting orange
*   - if there are no fresh oranges anymore, return minute count
*/
fun orangesRotting(grid: Array<IntArray>): Int {
    val rows = grid.size
    val cols = grid[0].size

    val q = ArrayDeque<Pair<Int, Int>>()
    var minutes = -1
    var fresh = 0

    // Queue evey rotten fruit
    // Sum every fresh fruit
    for (row in 0 until rows) { // O(m)
        for (col in 0 until cols) { // O(n)
            if (grid[row][col] == 2) q.addLast(row to col)
            if (grid[row][col] == 1) fresh++
        }
    }

    // If there is no fresh fruit we return 0 minutes
    if (fresh == 0) return 0

    val dirs = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    while(q.isNotEmpty()) {
        minutes++

        repeat (q.size) {
            val (row, col) = q.removeFirst()

            for ((r, c) in dirs) { // O(1)
                val newRow = row + r
                val newCol = col + c

                if (
                    newRow !in 0 until rows ||
                    newCol !in 0 until cols ||
                    grid[newRow][newCol] != 1
                ) {
                    continue
                }

                grid[newRow][newCol] = 2
                fresh--

                q.addLast(newRow to newCol)
            }
        }
    }

    return if (fresh == 0) minutes else -1
}
