package dtu.group21.models.pokemon

import android.media.Image

class Pokemon(
    val type: PokemonType,
    val secondaryType: PokemonType?,
    val gender: PokemonGender,
    val stats: PokemonStats,
    val spritePath: String
) {

}