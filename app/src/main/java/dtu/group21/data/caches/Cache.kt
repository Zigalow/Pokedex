package dtu.group21.data.caches

class Cache<T> {
    private val _elements = mutableListOf<T>()
    val elements get() = _elements.toList()

    /**
     * The maximum size of the cache. If the value is negative, there is no limit
     */
    var size = -1
        set(value) {
            field = value

            // Negative value indicates no limit
            if (field < 0)
                return

            // Remove the excess elements
            while (_elements.size > field) {
                purgeNext()
            }
        }

    private fun purgeNext() {
        // Nothing to purge
        if (_elements.size == 0)
            return

        // The element to purge
        _elements.removeAt(0)
    }

    fun add(element: T) {
        // Remove the old entry (if there is one), as it will now be put last in queue to be purged
        _elements.remove(element)
        _elements.add(element)

        // If the cache is full, purge the next element before adding this one
        if (_elements.size > this.size) {
            purgeNext()
        }
    }

    // Access operators
    operator fun contains(element: T) = (element in _elements)
}