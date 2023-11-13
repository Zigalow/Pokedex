package dtu.group21.models.pokemon

import androidx.compose.ui.graphics.Color

enum class PokemonType(val primaryColor: Color, val secondaryColor: Color) {
    NONE(Color.Transparent, Color.Transparent),
    NORMAL(Color(0xFFA8A878), Color(0xFFFFFFB8)),
    FIRE(Color(0xFFF08030), Color(0xFFFFD1B0)),
    WATER(Color(0xFF6890F0), Color(0xFFA8C1FF)),
    ELECTRIC(Color(0xFFF8D030), Color(0xFFFFEFAF)),
    GRASS(Color(0xFF97C64E), Color(0xFFCBFCB4)),
    ICE(Color(0xFF98D8D8), Color(0xFFB1FFFF)),
    FIGHTING(Color(0xFFC03028), Color(0xFFFFB6B2)),
    POISON(Color(0xFFA040A0), Color(0xFFFFB6FF)),
    GROUND(Color(0xFFE0C068), Color(0xFFFFEAB1)),
    FLYING(Color(0xFFA890F0), Color(0xFFC5B2FF)),
    PSYCHIC(Color(0xFFF85888), Color(0xFFFFB4CA)),
    BUG(Color(0xFFA8B820), Color(0xFFF7FFB4)),
    ROCK(Color(0xFFB8A038), Color(0xFFFFF1B3)),
    GHOST(Color(0xFF705898), Color(0xFFCFB1FF)),
    DRAGON(Color(0xFF7038F8), Color(0xFFC6AEFF)),
    DARK(Color(0xFF705848), Color(0xFFFFCFB0)),
    STEEL(Color(0xFFB8B8D0), Color(0xFFB1B1FF)),
    FAIRY(Color(0xFFF0B6BC), Color(0xFFFFB3BB));
}