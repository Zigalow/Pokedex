package dtu.group21.data.pokemon

import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonType

data class BasicPokemon(
    override val name: String,
    override val pokedexId: Int,
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    override val spriteId: String,
) : DisplayPokemon {
    override val hasTwoTypes = (secondaryType != PokemonType.NONE)
}
