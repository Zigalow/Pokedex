package dtu.group21.data.database

object ConverterHelper {
    fun <T> fromList(collection: List<T>?, converter: (T) -> String, separator: String = ";"): String? {
        return collection?.joinToString(separator, transform = converter)
    }
    fun <T> fromArray(array: Array<T>?, converter: (T) -> String, separator: String = ";"): String? {
        return fromList(array?.asList(), converter, separator)
    }

    fun <T> toList(string: String?, converter: (String) -> T, delimiter: String = ";"): List<T>? {
        return string?.split(delimiter)?.map { converter(it) }
    }
}