package dtu.group21.helpers

object StringHelper {
    fun toTitleCase(string: String, capitalizeAfter: String? = null): String {
        // Split it so all the chars to be capitalized are first in their own strings, in a list
        // If capitalizeAfter is null, it will just be a list with the original string as the only element
        val parts = string.split(capitalizeAfter ?: "")
        val capitalizedParts = parts.map { part -> part.replaceFirstChar { it.uppercase() } }
        return capitalizedParts.joinToString(capitalizeAfter ?: "")
    }
}