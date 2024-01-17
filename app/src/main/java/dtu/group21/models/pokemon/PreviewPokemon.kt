package dtu.group21.models.pokemon

import com.example.pokedex.R
import dtu.group21.data.pokemon.PokemonType

data class PreviewPokemon(
    val id: Int, // the same as the pokédex number
    val name: String,
    val type: PokemonType,
    val secondaryType: PokemonType,
) {
    val hasTwoTypes = secondaryType != PokemonType.NONE
}