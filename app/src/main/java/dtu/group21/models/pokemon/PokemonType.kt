package dtu.group21.models.pokemon

import androidx.compose.ui.graphics.Color

enum class PokemonType(val primaryColor: Color, val secondaryColor: Color) {
    NORMAL(Color(0xFF000000), Color(0xFF000000)),
    FIRE(Color(0xFFFD7D24), Color(0xFFFFCFAD)),
    WATER(Color(0xFF4592C4), Color(0xFFB6E2FF)),
    ELECTRIC(Color(0xFF000000), Color(0xFF000000)),
    GRASS(Color(0xFF9BCC50), Color(0xFFCBFCB4)),
    ICE(Color(0xFF000000), Color(0xFF000000)),
    FIGHTING(Color(0xFF000000), Color(0xFF000000)),
    POISON(Color(0xFFB97FC9), Color(0xFF000000)),
    GROUND(Color(0xFF000000), Color(0xFF000000)),
    FLYING(Color(0xFFA890F0), Color(0xFF000000)),
    PSYCHIC(Color(0xFF000000), Color(0xFF000000)),
    BUG(Color(0xFF729F3F), Color(0xFFDBFCB5)),
    ROCK(Color(0xFF000000), Color(0xFF000000)),
    GHOST(Color(0xFF000000), Color(0xFF000000)),
    DRAGON(Color(0xFF000000), Color(0xFF000000)),
    DARK(Color(0xFF000000), Color(0xFF000000)),
    STEEL(Color(0xFF000000), Color(0xFF000000)),
    FAIRY(Color(0xFF000000), Color(0xFF000000));


}