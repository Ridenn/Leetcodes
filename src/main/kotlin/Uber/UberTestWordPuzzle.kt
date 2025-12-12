package Uber

/*

# Given a two-dimensional array of letters, find a given word written in any of the 8 directions.
# I.e.

# EXAMPLE
# Input: UBER

# Input:
# 'A',  'U',  'I',  'K',  'F',  'W',  'N'
# 'W',  'Q',  'B',  'O',  'L' , 'X',  'P'
# 'T' , 'L',  'A',  'E',  'R',  'E',  'C'
# 'Y',  'Z', 'X',   'E',  'R',  'L',  'W'

# Output: true

# EXAMPLE
# Input: UBER

# Input
# ‘U’, ‘B’
# ‘E’, ‘R’

# Output: false

*/

fun findWordInPuzzle(grid: Array<CharArray>, word: String): Boolean {
    val rows = grid.size
    val cols = grid[0].size

    val directions = arrayOf(
        -1 to 0,  // left
        1 to 0,   // right
        0 to -1,  // up
        0 to 1,   // down
        -1 to -1, // left-up
        1 to 1,   // right-down
        -1 to 1,  // right-up
        1 to -1   // left-down
    )

    fun dfs(row: Int, col: Int, dirs: Array<Pair<Int, Int>>, index: Int): Boolean {
        if (index == word.length) return true

        if (
            row !in 0 until rows ||
            col !in 0 until cols ||
            grid[row][col] != word[index]
        ) {
            return false
        }

        for ((r, c) in dirs) {
            val newRow = row + r
            val newCol = col + c

            val hasWord = dfs(newRow, newCol, arrayOf(r to c), index + 1)
            if (hasWord) return true
        }
        return false
    }

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            if (grid[row][col] == word[0]) {
                val hasWord = dfs(row, col, directions, 0)
                if (hasWord) return true
            }
        }
    }
    return false
}

fun findWordInPuzzle2(grid: Array<CharArray>, word: String): Boolean {
    val rows = grid.size
    val cols = grid[0].size

    val directions = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1, 1 to 1, -1 to 1, 1 to -1, -1 to -1)

    fun findWordDFS(row: Int, col: Int, index: Int, dirs: Array<Pair<Int, Int>>): Boolean {
        if (index == word.length) return true

        if (
            row !in 0 until rows ||
            col !in 0 until cols ||
            grid[row][col] != word[index]
        ) {
            return false
        }

        for ((r, c) in dirs) {
            val newRow = row + r
            val newCol = col + c

            val hasWord = findWordDFS(newRow, newCol, index + 1, arrayOf(r to c))
            if (hasWord) return true
        }
        return false
    }

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            if (grid[row][col] == word[0]) {
                val hasWord = findWordDFS(row, col, 0, directions)
                if (hasWord) return true
            }
        }
    }
    return false
}

fun findWordPuzzle3(grid: Array<CharArray>, word: String): Boolean {
    val rows = grid.size
    val cols = grid[0].size

    val directions = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1, -1 to -1, 1 to 1, 1 to -1, -1 to 1)

    fun dfs(row: Int, col: Int, wordIndex: Int, dirs: Array<Pair<Int, Int>>): Boolean {
        if (wordIndex == word.length) return true

        if (
            row !in 0 until rows ||
            col !in 0 until cols ||
            grid[row][col] != word[wordIndex]
        ) {
            return false
        }

        for ((r, c) in dirs) {
            val newRow = row + r
            val newCol = col + c

            val hasWord = dfs(newRow, newCol, wordIndex + 1, arrayOf(r to c))
            if (hasWord) return true
        }
        return false
    }

    for (row in 0 until rows) { // O(m)
        for (col in 0 until cols) { // O(n)
            if (grid[row][col] == word[0]) {
                val hasWord = dfs(row, col, 0, directions)
                if (hasWord) return true
            }
        }
    }
    return false
}

fun main() {
    // true
    println(
        findWordPuzzle3(arrayOf(
        charArrayOf('A',  'U',  'I',  'K',  'F',  'W',  'N'),
        charArrayOf('W',  'Q',  'B',  'E',  'L' , 'X',  'P'),
        charArrayOf('T' , 'L',  'A',  'E',  'R',  'E',  'C'),
        charArrayOf('Y',  'Z', 'X',   'E',  'R',  'L',  'W'),
    ), "UBER")
    )

    // false
    println(
        findWordPuzzle3(arrayOf(
        charArrayOf('U',  'B'),
        charArrayOf('E',  'R'),
    ), "UBER")
    )
}
