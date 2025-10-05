package leetcodes

fun main() {
    var isFirstCase = true

    while (true) {
        val count = readLine()?.toIntOrNull() ?: break
        if (count == 0) break

        val phraseMap = mutableListOf<String>()
        var largerNumIndex = -1

        repeat(count) {
            val phraseTrimmed = readLine()?.replace("\\s+".toRegex(), " ")?.trim()  ?: ""
            phraseMap.add(phraseTrimmed)
            largerNumIndex = maxOf(phraseTrimmed.length, largerNumIndex)
        }

        if (!isFirstCase) println()
        isFirstCase = false

        phraseMap.forEach { phrase ->
            val spaces = " ".repeat(largerNumIndex - phrase.length)
            println("$spaces$phrase")
        }
    }
}

/*
while (true) {
        val n = readLine()?.toIntOrNull() ?: break
        if (n == 0) break

        val lines = mutableListOf<String>()
        var maxLength = 0

        // Lê as linhas e processa os espaços extras
        repeat(n) {
            val line = readLine()?.replace("\\s+".toRegex(), " ")?.trim() ?: ""
            lines.add(line)
            maxLength = maxOf(maxLength, line.length)
        }

        // Imprime as linhas justificadas à direita
        lines.forEach { line ->
            val spaces = " ".repeat(maxLength - line.length)
            println("$spaces$line")
        }

        // Imprime uma linha em branco entre os casos de teste
        println()
    }
*/