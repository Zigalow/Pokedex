package dtu.group21.models.pokemon

import android.media.Image
import androidx.lifecycle.viewmodel.savedstate.R

class Pokemon(
    val name: String,
    val pokedexNumber: Int,
    val type: PokemonType,
    val secondaryType: PokemonType,
    val gender: PokemonGender,
    val stats: PokemonStats,
    val spriteResourceId: Int
) {

}