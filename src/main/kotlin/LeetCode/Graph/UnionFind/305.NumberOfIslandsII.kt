package LeetCode.Graph.UnionFind

/*
You start with an m x n grid that represents a map where all cells are initially water (represented by 0).
You can perform operations to add land (represented by 1) to specific positions on this map.

Given an array positions where positions[i] = [ri, ci] represents the coordinates where you want to add land in the i-th operation,
you need to track how many islands exist after each operation.

An island is defined as a group of adjacent land cells (1s) that are connected horizontally or vertically.
Land cells that are only diagonally adjacent do not form the same island. The entire grid is surrounded by water.

Your task is to return an array answer where answer[i] represents the total number of islands present in the grid after
performing the i-th operation (adding land at position [ri, ci]).

For example:

    If you add land to an isolated water cell, you create a new island (island count increases by 1)
    If you add land adjacent to existing land, you extend that island (island count stays the same)
    If you add land between two or more separate islands, you connect them into one island (island count decreases)
    If you try to add land to a position that's already land, nothing changes (island count stays the same)

The solution uses a Union-Find data structure to efficiently track and merge islands as land is added.
Each position in the grid is mapped to a unique index i * n + j for use in the union-find structure.
When adding new land, the algorithm checks all four adjacent cells and merges connected components as needed,
updating the island count accordingly.
*/

fun main() {
    println(numOfIslands2Remade(3, 3, arrayOf(0 to 0, 0 to 1, 2 to 1)).contentToString()) // [1, 1, 2]
    println(numOfIslands2Remade(3, 3, arrayOf(0 to 0, 0 to 1, 6 to 1)).contentToString()) // [] -> input inválido
}

class UnionFind2(size: Int) {
    val parent = IntArray(size) { it }

    fun find(node: Int): Int {
        if (parent[node] == node) return node
        return find(parent[node])
    }

    fun unite(node1: Int, node2: Int): Boolean {
        if (find(node1) == find(node2)) return false
        parent[find(node1)] = find(node2)
        return true
    }
}

fun numOfIslands2Remade(m: Int, n: Int, positions: Array<Pair<Int, Int>>): IntArray {
    val grid = Array(m) { IntArray(n) { 0 } }
    val result = IntArray(positions.size) { 0 }
    var numOfIslands = 0

    val uf = UnionFind2(m * n)

    val directions = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    for (posIdx in positions.indices) {
        val row = positions[posIdx].first
        val col = positions[posIdx].second

        if (
            row !in 0 until m ||
            col !in 0 until n
        ) {
            return intArrayOf()
        }

        if (grid[row][col] == 1) {
            result[posIdx] = numOfIslands
            continue
        }

        grid[row][col] = 1
        numOfIslands++

        for ((r, c) in directions) {
            val newRow = row + r
            val newCol = col + c

            if (
                newRow !in 0 until m ||
                newCol !in 0 until n ||
                grid[newRow][newCol] == 0
            ) {
                continue
            }

            val currentPosId = row * n + col
            val neighPosId = newRow * n + col

            if (uf.unite(currentPosId, neighPosId)) numOfIslands--
        }

        result[posIdx] = numOfIslands
    }
    return result
}

fun numberOfIslands2(m: Int, n: Int, positions: Array<Pair<Int, Int>>) : IntArray {
    /*
    * Time complexity: O(k × α(m * n)) -> O(m * n) dropping constants
    *   - k = number of positions
    *   - α = inverse Ackermann function ≈ constant
    * Space complexity: O(m * n) -> we build a grid m * n
    * Approach:
    * Intuition:
    *
    * */

    val grid = Array(m) { IntArray(n) { 0 } } // Inicia o grid com todas as posições como água (0)
    val result = IntArray(positions.size) { 0 }
    var numOfIslands = 0

    val directions = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    val uf = UnionFind(m * n)

    for (i in positions.indices) {
        val row = positions[i].first
        val col = positions[i].second

        // Valida se posicao é valida
        // NÃO NECESSÁRIO SE INPUT GARANTIR SEMPRE SER VÁLIDO
        if (
            row !in 0 until m ||
            col !in 0 until n
        ) {
            return intArrayOf()
        }

        if (grid[row][col] == 1) {
            result[i] = numOfIslands
            continue
        }

        numOfIslands++ // Aumenta o número de ilhas
        grid[row][col] = 1

        val currPosId = row * n + col

        for ((r, c) in directions) {
            val newRow = row + r
            val newCol = col + c

            if (
                newRow !in 0 until m ||
                newCol !in 0 until n ||
                grid[newRow][newCol] == 0 // Se vizinho é água, não fazemos nada
            ) {
                continue
            }

            val neighPosId = newRow * n + newCol

            // Volta true se conseguiu juntar as terras em uma ilha só
            val couldUnite = uf.unite(currPosId, neighPosId)
            if (couldUnite) numOfIslands-- // Se duas terras virou 1 só, tira 1 ilha
        }

        result[i] = numOfIslands
    }

    return result
}

class UnionFind(size: Int) {
    private val parent = IntArray(size) { it } // Cada nó é criado como pai/representante de si mesmo
    private val setSize = IntArray(size) { 1 } // Cada conjunto inicia com tamanho de 1 nó

    fun find(node: Int): Int {
        if (parent[node] == node) {
            println("Representante: $node")
            return node
        }
        return find(parent[node])
    }

    fun unite(nodeA: Int, nodeB: Int): Boolean {
        val reprA = find(nodeA)
        val reprB = find(nodeB)

        // Se nós A e B tiverem mesmo pai, não faz union
        if (reprA == reprB) return false

        // Se conjunto A for maior a conjunto B
        if (setSize[reprA] > setSize[reprB]) {
            parent[reprB] = reprA // Aponta representante de B pra A
            setSize[reprA] += setSize[reprB] // Soma o tamanho do conjunto B no size do conjunto A
        } else {
            parent[reprA] = reprB // Aponta representante de A pra B
            setSize[reprB] += setSize[reprA] // Soma o tamanho do conjunto A no size do conjunto B
        }

        return true
    }
}
