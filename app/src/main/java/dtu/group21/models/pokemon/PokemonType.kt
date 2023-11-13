package dtu.group21.models.pokemon

import androidx.compose.ui.graphics.Color

enum class PokemonType(val primaryColor: Color, val secondaryColor: Color) {
    NONE(Color.Transparent, Color.Transparent),
    NORMAL(Color(0xFFA8A878), Color(0xFFFFFFB8)),
    FIRE(Color(0xFFF08030), Color(0xFFFFD1B0)),
    WATER(Color(0xFF6890F0), Color(0xFFA8C1FF)),
    ELECTRIC(Color(0xFFF8D030), Color(0xFFFFEFAF)),
    GRASS(Color(0xFF97C64E), Color(0xFFCBFCB4)),
    ICE(Color(0xFF5CB1D8), Color(0xFFB2E0FF)),
    FIGHTING(Color(0xFFC03028), Color(0xFFFFB6B2)),
    POISON(Color(0xFFA040A0), Color(0xFFFFB6FF)),
    GROUND(Color(0xFFE0C068), Color(0xFFFFEAB1)),
    FLYING(Color(0xFF2E9999), Color(0xFFA7E0E0)),
    PSYCHIC(Color(0xFFDB3063), Color(0xFFFF96B4)),
    BUG(Color(0xFFA8B820), Color(0xFFF7FFB4)),
    ROCK(Color(0xFFB38E31), Color(0xFFDDC478)),
    GHOST(Color(0xFF68489C), Color(0xFFCEB2FA)),
    DRAGON(Color(0xFF6431E4), Color(0xFFA081E9)),
    DARK(Color(0xFF705848), Color(0xFFFFCFB0)),
    STEEL(Color(0xFF4D5E6D), Color(0xFF8C9EAD)),
    FAIRY(Color(0xFFC2535E), Color(0xFFD8949B));
}