package LeetCode.Graph

import java.lang.StringBuilder

fun main() {
    println(foreignDictionaryKahn(listOf("hrn","hrf","er","enn","rfnn")))
}

fun foreignDictionary(words: List<String>): String {
    /*
    * Input: ["hrn","hrf","er","enn","rfnn"]
    * Output: "hernf"
    */

    // char -> { char1, char2, char3... }
    val wSize = words.size
    val graph = mutableMapOf<Char, MutableSet<Char>>()

    for (word in words) {
        for (char in word) {
            graph.getOrPut(char) { mutableSetOf() }
        }
    }

    // Só vamos até wSize - 1, pois olhamos word[wIdx] e word[wIdx + 1]
    // Parando word1 em wSize - 1, garantimos que não dará ArrayOutOfBounds na word2
    for (wIdx in 0 until wSize - 1) {
        val word1 = words[wIdx]
        val word2 = words[wIdx + 1]

        val minLength = minOf(word1.length, word2.length)

        if (word1.length > word2.length &&
            word1.substring(0, minLength) == word2.substring(0, minLength)
        ) {
            return ""
        }

        for (i in 0 until minLength) {
            // Se os caracteres forem iguais, continua até achar diferentes
            if (word1[i] == word2[i]) continue
            graph[word1[i]]?.add(word2[i])
            break
        }
    }

    val visited = mutableMapOf<Char, Boolean>()
    val result = mutableListOf<Char>()

    fun dfs(char: Char): Boolean {
        if (char in visited) return visited[char] == true

        visited[char] = true

        for (neigh in graph[char].orEmpty()) {
            val hasCycle = dfs(neigh)
            if (hasCycle) return true
        }

        result.add(char)
        visited[char] = false

        return false
    }

    // Varre cada char key de graph
    for (char in graph.keys) {
        val hasCycle = dfs(char)
        if (hasCycle) return ""
    }

    return result.reversed().joinToString("")
}

fun foreignDictionaryKahn(words: List<String>): String {
    // Uma mesma letra pode se repetir em uma palavra, então queremos ela apenas uma vez, por isso o set
    val graph = mutableMapOf<Char, MutableSet<Char>>()
    val indegree = mutableMapOf<Char, Int>()

    for (word in words) {
        for (char in word) {
            graph.getOrPut(char) { mutableSetOf() }
            indegree.getOrPut(char) { 0 }
        }
    }

    for (wIdx in 0 until words.size - 1) {
        val word1 = words[wIdx]
        val word2 = words[wIdx + 1]
        val minLength = minOf(word1.length, word2.length)

        // Se word1 é maior que word2
        // E word2 é um prefixo exato de word1:
        // Ex: ["abc", "ab"] -> "abc" não pode vir antes de seu prefixo "ab"
        if (
            word1.length > word2.length &&
            word1.substring(0, minLength) == word2.substring(0, minLength)
        ) {
            return ""
        }

        // Roda pegando apenas a quantidade de caracteres mínimos (que existem nos 2 words)
        for (i in 0 until minLength) {
            // Se achou caracter diferente
            if (word1[i] != word2[i]) {
                // E se caracter da word2 ainda não existir como adjacencia da word1, adiciona
                if (word2[i] !in graph[word1[i]].orEmpty()) {
                    graph[word1[i]]?.add(word2[i])
                    indegree[word2[i]] = indegree.getOrDefault(word2[i], 0) + 1
                }
                break // só o primeiro caractere diferente importa para determinar a ordem
            }
        }
    }

    val q = ArrayDeque<Char>()

    for ((char, degree) in indegree) {
        if (degree == 0) q.addLast(char)
    }

    val result = StringBuilder()

    while (q.isNotEmpty()) {
        val currChar = q.removeFirst()

        result.append(currChar)

        for (neigh in graph[currChar].orEmpty()) {
            indegree[neigh] = indegree.getOrDefault(neigh, 0) - 1
            if (indegree[neigh] == 0) q.addLast(neigh)
        }
    }
    // indegree possui todas as letras, então se o result não tiver todas
    // retornamos string vazia, senão retornamos o próprio result com a ordem correta
    return if (result.length != indegree.size) "" else result.toString()
}
