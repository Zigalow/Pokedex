package dtu.group21.data.pokemon

import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType

data class BasicStatPokemon(
    override val name: String,
    override val pokedexId: Int,
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    override val spriteId: String,
    val stats: PokemonStats
) : StatPokemon {
    override val hp: Int
        get() = stats.hp
    override val attack: Int
        get() = stats.attack
    override val defense: Int
        get() = stats.defense
    override val specialAttack: Int
        get() = stats.specialAttack
    override val specialDefense: Int
        get() = stats.specialDefense
    override val speed: Int
        get() = stats.speed
    override val hasTwoTypes = (secondaryType != PokemonType.NONE)
}
