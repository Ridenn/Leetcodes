package LeetCode.Graph.Dijkstra

import java.util.*

fun dijkstra(n: Int, edges: Array<IntArray>, start: Int): IntArray {
    // 1️⃣ Grafo (adjacency list)
    val graph = Array(n) { mutableListOf<Pair<Int, Int>>() }
    for (edge in edges) {
        val (node, neighbor, weight) = edge
        graph[node].add(neighbor to weight)
        // se for bidirecional:
        // graph[neighbor].add(node to weight)
    }

    // 2️⃣ Distâncias iniciais
    // Todas as distâncias iniciam em infinito, menos a do início, que é 0
    val dist = IntArray(n) { Int.MAX_VALUE }
    dist[start] = 0

    // 3️⃣ Min-heap (node -> distance)
    val minHeap = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
    minHeap.offer(start to 0)

    // 4️⃣ Visitados
    val visited = BooleanArray(n) //mutableSetOf

    // 5️⃣ BFS ponderado (Dijkstra)
    while (minHeap.isNotEmpty()) {
        val (node, weight) = minHeap.poll()
        if (visited[node]) continue
        visited[node] = true

        for ((neigh, w) in graph[node]) {
            val newDist = weight + w
            if (newDist < dist[neigh]) {
                dist[neigh] = newDist
                minHeap.offer(neigh to newDist)
            }
        }
    }

    return dist
}

data class Position(val row: Int, val col: Int)

