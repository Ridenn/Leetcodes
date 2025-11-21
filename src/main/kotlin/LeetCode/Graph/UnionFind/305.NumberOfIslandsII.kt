package LeetCode.Graph.UnionFind

fun main() {
    println(numberOfIslands2(3, 3, arrayOf(0 to 0, 0 to 1, 2 to 1)).contentToString()) // [1, 1, 2]
    println(numberOfIslands2(3, 3, arrayOf(0 to 0, 0 to 1, 6 to 1)).contentToString()) // [] -> input inválido
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
