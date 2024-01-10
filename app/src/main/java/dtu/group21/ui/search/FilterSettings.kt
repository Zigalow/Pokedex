package dtu.group21.ui.search

class FilterSettings {
    enum class FilterType {
        IncludableTypes,
        ExactType,
    }

    enum class FilterOptions {
        TYPEs,
        GENERATIONS
    }

    var filterType = FilterType.IncludableTypes
    val types: ArrayList<Boolean> = arrayListOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)


    fun numberOfTypesChosen(): Int {
        var counter = 0
        for (i in types) {
            if (i) {
                counter++
            } 
        }
        return counter
    }
    
    fun reset() {
        filterType = FilterType.IncludableTypes
        for (i in 0 until types.size) {
            types[i] = false
        }
    }

    fun hasSettings() = (types.contains(true))
}