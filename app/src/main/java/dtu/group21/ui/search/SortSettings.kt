package com.example.search

class SortSettings {
    enum class SortType {
        Ascending,
        Descending,
    }

    var sortType = SortType.Ascending
    var sortMethod: Int = 0

    fun reset() {
        sortType = SortType.Ascending
        sortMethod = 0
    }

    fun hasSettings() = (sortType != SortType.Ascending) || (sortMethod != 0)
}