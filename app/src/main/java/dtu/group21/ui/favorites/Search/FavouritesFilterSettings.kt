package dtu.group21.ui.favorites.Search

class FavouritesFilterSettings {
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

    fun hasSettings() = (filterType != FilterType.IncludableTypes) || (types.contains(true))
}