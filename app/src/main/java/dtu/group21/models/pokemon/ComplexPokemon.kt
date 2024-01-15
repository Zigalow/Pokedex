package dtu.group21.models.pokemon

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.GenderedPokemon
import dtu.group21.models.pokemon.moves.BasicMove
import dtu.group21.models.pokemon.moves.DisplayMove

//@Entity(tableName = "favorites")
class ComplexPokemon(
    //@PrimaryKey
    override val pokedexId: Int, // the same as the pokédex number
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    override val gender: PokemonGender,
    override val category: String,
    override val abilities: Array<PokemonAbility>,
    override val weightInGrams: Int,
    override val heightInCm: Int,
    override val stats: PokemonStats,
    val species: PokemonSpecies,
    override val moves: Array<DisplayMove>,
    override var isFavorite: MutableState<Boolean> = mutableStateOf(false)
) : GenderedPokemon, DetailedPokemon {
    //@Ignore
    override val spriteId = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${
        pokedexId.toString().padStart(3, '0')
    }.png"
    override val evolutionChainId: Int
        get() = -1 // TODO:  
    override val genderRate: Int
        get() = species.genderRate
    override val isBaby: Boolean
        get() = species.isBaby
    override val isLegendary: Boolean
        get() = species.isLegendary
    override val isMythical: Boolean
        get() = species.isMythical
    override val generation: Int
        get() = -1 // TODO:
    override val hasGenderDifferences: Boolean
        get() = species.hasGenderDifferences
    
    override val name = species.name

    //@Ignore
    override val hasTwoTypes = secondaryType != PokemonType.NONE
}