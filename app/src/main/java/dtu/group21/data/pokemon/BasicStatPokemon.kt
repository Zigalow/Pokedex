package dtu.group21.data.pokemon

import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType

data class BasicStatPokemon(
    override val name: String,
    override val pokedexId: Int,
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    override val spriteId: String,
    override val stats: PokemonStats
) : StatPokemon {
    override val hasTwoTypes = (secondaryType != PokemonType.NONE)
}
