package dtu.group21.ui.search

class FilterSettings {
    enum class FilterType {
        IncludableTypes,
        DualType,
    }

    var filterType = FilterType.IncludableTypes
    val types: ArrayList<Boolean> = arrayListOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)

    fun reset() {
        filterType = FilterType.IncludableTypes
        for (i in 0 until types.size) {
            types[i] = false
        }
    }

    fun hasSettings() = (types.contains(true))
}