package dtu.group21.data.caches

import java.util.function.Predicate

open class Cache<T> {
    protected val _elements = mutableListOf<T>()
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

    /**
     * Refresh this element in the cache, making it last in queue to be purged.
     * If the element is not in the cache, this method does nothing.
     */
    fun refresh(element: T) {
        // Don't refresh if it's not even in the cache
        if (element !in this)
            return

        _elements.remove(element)
        _elements.add(element)
    }

    /**
     * Add an element to this cache, if the element is already in the cache, acts like refresh(element).
     * This method could potentially purge elements from the cache, to make room for this element.
     */
    fun add(element: T) {
        if (element in this) {
            refresh(element)
            return
        }

        _elements.add(element)
        if (_elements.size > this.size) {
            purgeNext()
        }
    }

    fun removeIf(filter: Predicate<T>) {
        _elements.removeIf(filter)
    }

    // Access operators
    operator fun contains(element: T) = (element in _elements)
}