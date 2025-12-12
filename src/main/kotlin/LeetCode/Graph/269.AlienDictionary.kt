package LeetCode.Graph

import java.lang.StringBuilder

/*
You're given a list of words from an alien language that uses the same 26 letters as English, but the alphabetical order of these letters is different and unknown to you.

The words are claimed to be sorted in dictionary order according to this alien alphabet. Your task is to:

    Verify if the claim is valid: Check if the given word order can actually correspond to some valid alphabetical ordering of letters.
    If not, return an empty string "".

    Recover the alien alphabet: If the ordering is valid, determine what the alphabetical order of letters must be in this alien
    language and return it as a string containing all unique letters that appear in the words, sorted according to the alien alphabet's rules.

Key Points:

    The words should follow lexicographic ordering based on the alien alphabet (like how words are ordered in a dictionary)
    You need to deduce the relative order of letters by comparing adjacent words in the list
    If there are multiple valid orderings possible, return any one of them
    If the given arrangement is impossible (contradicts itself), return ""

Example of Invalid Input: If you have words like ["abc", "ab"], this would be invalid because in any alphabet,
"abc" cannot come before "ab" in dictionary order (a longer word with the same prefix must come after the shorter word).

Example of Deducing Order: If you have words ["wrt", "wrf"], you can deduce that 't' comes before 'f' in the alien alphabet
since these words differ at the third position and "wrt" comes before "wrf".

The solution uses topological sorting to build the alphabet order from the character relationships derived from comparing adjacent words.
*/

fun main() {
    println(alienDictionary(listOf("hrn","hrf","er","enn","rfnn"))) // hernf
    println(alienDictionary(listOf("hrfn","hrf","er","enn","rfnn"))) // ""
    println(alienDictionary(listOf("z","x"))) // zx
    println(alienDictionary(listOf("baa", "abcd", "abca", "cab", "cad"))) // bdac
    println(alienDictionary(listOf("a", "b", "c"))) // abc
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

fun alienDictionary(words: List<String>): String {
    val graph = mutableMapOf<Char, MutableSet<Char>>()
    val indegree = mutableMapOf<Char, Int>()

    for (word in words) {
        for (char in word) {
            graph.getOrPut(char) { mutableSetOf() }
            indegree.getOrPut(char) { 0 }
        }
    }

    for (wIndex in 0 until words.size - 1) {
        val word1 = words[wIndex]
        val word2 = words[wIndex + 1]
        val minLength = minOf(word1.length, word2.length)

        if (word1.length > word2.length && word1.substring(0, minLength) == word2.substring(0, minLength)) return ""

        for (index in 0 until minLength) {
            if (word1[index] != word2[index]) {
                if (word2[index] !in graph[word1[index]].orEmpty()) {
                    graph[word1[index]]?.add(word2[index])
                    indegree[word2[index]] = indegree.getOrDefault(word2[index], 0) + 1
                }
                break
            }
        }
    }

    val q = ArrayDeque<Char>()
    for ((char, degree) in indegree) {
        if (degree == 0) q.addLast(char)
    }

    var result = ""

    while (q.isNotEmpty()) {
        val currentChar = q.removeFirst()

        result += currentChar

        for (neighChar in graph[currentChar].orEmpty()) {
            indegree[neighChar] = indegree.getOrDefault(neighChar, 0) - 1
            if (indegree[neighChar] == 0) q.addLast(neighChar)
        }
    }

    return result
}













