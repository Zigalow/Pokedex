package dtu.group21.ui.search

object SortSettings {
    enum class SortType {
        Ascending,
        Descending,
    }

    var sortType = SortType.Ascending
    var sortMethod = SortMethod.ID

    enum class SortMethod {
        ID,
        NAME,
        HP,
        ATTACK,
        DEFENSE,
        SPECIAL_ATTACK,
        SPECIAL_DEFENSE,
        SPEED,
        TOTAL
    }
    
    fun reset() {
        sortType = SortType.Ascending
        sortMethod = SortMethod.ID
    }

    fun hasSettings() = (sortType != SortType.Ascending) || (sortMethod != SortMethod.ID)
}