package study

fun main() {
    CompanionTestes().main()
    /* saída esperada:
    0
    10
    -1 -> var notSaved nao é salva por não ser static
     */
}

class CompanionTestes {
    var notSaved = -1

    fun main() {
        println(savedValue)
        Class2().main()
        println(savedValue)
        println(notSaved)
    }

    companion object {
        var savedValue = 0
    }
}

class Class2 {
    fun main() {
        CompanionTestes.savedValue = 10
        CompanionTestes().notSaved = 20
    }
}