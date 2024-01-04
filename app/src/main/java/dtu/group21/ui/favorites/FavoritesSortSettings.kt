package dtu.group21.ui.favorites

class FavoritesSortSettings {
    enum class SortType {
        Ascending,
        Descending,
    }

    var favoritesSortType = SortType.Ascending
    var favoritesSortMethod: Int = 0

    fun reset() {
        favoritesSortType = SortType.Ascending
        favoritesSortMethod = 0
    }

    fun hasSettings() = (favoritesSortType != SortType.Ascending) || (favoritesSortMethod != 0)
}