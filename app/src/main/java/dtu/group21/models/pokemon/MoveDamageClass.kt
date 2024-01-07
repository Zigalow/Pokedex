package dtu.group21.models.pokemon

import androidx.compose.ui.graphics.Color

enum class MoveDamageClass(val color: Color) {
    PHYSICAL(Color(0xFFBB311E)),
    SPECIAL(Color(0xFF3F6781)),
    STATUS(Color(0xFF888B9A));

    companion object {
        fun getFromName(name: String): MoveDamageClass = when (name.lowercase()) {
            "physical" -> PHYSICAL
            "special" -> SPECIAL
            "status" -> STATUS
            else -> PHYSICAL
        }
    }
}