package LeetCode.DynamicProgramming

fun main() {
    println(wordBreak("catsandog", listOf("cats", "dog", "sand", "and", "cat"))) // false
    println(wordBreak("terranova", listOf("terra", "nova"))) // true
    println(wordBreak("terranovazul", listOf("terra", "nova", "azul"))) // false
}

fun wordBreak(s: String, wordDict: List<String>): Boolean {
    // s.length + 1 para poder guardar a posição final "" = true
    // Posição final => "" é sempre segmentável (true)
    val canBreakFromIdxDp = BooleanArray(s.length + 1) { false }
    canBreakFromIdxDp[s.length] = true

    // Loop de trás pra frente
    for (start in s.length - 1 downTo 0) {
        // Testa cada word para ver se encontra na string
        for (word in wordDict) {
            val end = start + word.length // posição de start até o final da string

            // Se posição final existe dentro do tamanho da string
            // E se substring de start e end da string é == word
            if (end <= s.length && word == s.substring(start, end)) {
                // Se achar uma word válida na posição start to end
                // Valida se a posição end estava válida, ou seja, se uma letra nela já não tinha sido usada
                // Se OK, agora valida o início da word atual como começo de um novo segmento
                if (canBreakFromIdxDp[end]) {
                    canBreakFromIdxDp[start] = true
                    break
                }
            }
        }
    }
    return canBreakFromIdxDp[0]
}
