package dtu.group21.helpers

object PokemonHelper {
    val validIds = 1..1025
    val validGenerations = 1..9

    fun getGeneration(pokedexId: Int): Int {
        val generation = when (pokedexId) {
            in 1..151 -> 1
            in 152..251 -> 2
            in 252..386 -> 3
            in 387..493 -> 4
            in 494..649 -> 5
            in 650..721 -> 6
            in 722..809 -> 7
            in 810..905 -> 8
            in 906..1025 -> 9
            else -> 0
        }

        return generation
    }

    fun generationString(generationNumber: Int): String {
        val generationString = when (generationNumber) {
            1, 2, 3 -> "I".repeat(generationNumber)
            4 -> "IV"
            5, 6, 7, 8 -> "V" + "I".repeat(generationNumber - 5)
            9 -> "IX"
            else -> ""
        }

        return generationString
    }

    fun getPokedexIdString(pokedexId: Int) = pokedexId.toString().padStart(3, '0')

    fun getEnglishName(pokedexId: Int, idName: String): String {
        val name = when (pokedexId) {
            // All the names where there is extra information baked into the idName, which can just be thrown away
            386, 413, 487, 492, 550, 555, 641, 642, 645, 647, 648, 678, 681, 710, 711, 718, 741, 745, 746, 774, 778, 849, 875, 876, 877, 892, 902, 905 -> StringHelper.toTitleCase(idName).split('-').first()

            // All the names where the '-' is a stand-in for ' ', and all words are capitalized
            785, 786, 787, 788, 984, 985, 986, 987, 988, 989, 990, 991, 992, 993, 994, 995, 1005, 1006, 1009, 1010 -> StringHelper.toTitleCase(idName, "-").replace("-", " ")
            // All the names where the '-' is actually part of the name, and all words are capitalized
            250, 474, 1001, 1002, 1003, 1004 -> StringHelper.toTitleCase(idName, "-")

            // Mr. Mime and Mr. Rime
            122, 866 -> StringHelper.toTitleCase(idName, "-").replace("-", ". ")
            // Farfetch'd and Sirfetch'd
            83, 865 -> StringHelper.toTitleCase(idName.replace("fetchd", "fetch’d"))
            // Nidoran female
            29 -> "Nidoran♀"
            // Nidoran male
            32 -> "Nidoran♂"
            // Mime Jr.
            439 -> "Mime Jr."
            // Flabébé
            669 -> "Flabébé"
            // Type: Null
            772 -> "Type: Null"
            // All the ones where the name is just lowercase (most of them)
            else -> StringHelper.toTitleCase(idName)
        }

        return name
    }
}