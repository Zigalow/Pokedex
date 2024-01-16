package dtu.group21.data.pokemon

import androidx.compose.runtime.MutableState
import dtu.group21.data.pokemon.moves.PokemonMove

data class AdvancedPokemon(
    override val name: String,
    override val pokedexId: Int,
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    override val moves: Array<PokemonMove>,
    override val stats: PokemonStats,
    override val evolutionChainId: Int,
    override val genderRate: Int,
    override val isBaby: Boolean,
    override val isLegendary: Boolean,
    override val isMythical: Boolean,
    override val category: String,
    override val generation: Int,
    override val weightInGrams: Int,
    override val heightInCm: Int,
    override val abilities: Array<PokemonAbility>,
    override val spriteId: String
) : DetailedPokemon {
    override val hasTwoTypes = (secondaryType != PokemonType.NONE)

    constructor(
        template: DisplayPokemon,
        moves: Array<PokemonMove>,
        stats: PokemonStats,
        evolutionChainId: Int,
        genderRate: Int,
        isBaby: Boolean,
        isLegendary: Boolean,
        isMythical: Boolean,
        category: String,
        generation: Int,
        weightInGrams: Int,
        heightInCm: Int,
        abilities: Array<PokemonAbility>,
    ) : this(
        name = template.name,
        pokedexId = template.pokedexId,
        primaryType = template.primaryType,
        secondaryType = template.secondaryType,
        moves,
        stats,
        evolutionChainId,
        genderRate,
        isBaby,
        isLegendary,
        isMythical,
        category,
        generation,
        weightInGrams,
        heightInCm,
        abilities,
        spriteId = template.spriteId
    )
}
