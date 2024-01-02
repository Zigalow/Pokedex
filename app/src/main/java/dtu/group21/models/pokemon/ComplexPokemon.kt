package dtu.group21.models.pokemon

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

//@Entity(tableName = "favorites")
class ComplexPokemon (
    //@PrimaryKey
    override val pokedexId: Int, // the same as the pokédex number
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    val gender: PokemonGender,
    val categoryName: String,
    override val abilities: Array<PokemonAbility>,
    override val weightInGrams: Int,
    override val heightInCm: Int,
    val stats: PokemonStats,
    val species: PokemonSpecies,
    override val moves: Array<PokemonMove>,
    override var isFavorite: MutableState<Boolean> = mutableStateOf(false)
) : DisplayPokemon, DetailedPokemon {
    //@Ignore
    override val spriteId = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${
        pokedexId.toString().padStart(3, '0')
    }.png"
    override val evolutionChainId: Int
        get() = evolutionChainId
    override val genderRate: Int
        get() = TODO("Not yet implemented")
    override val isBaby: Boolean
        get() = TODO("Not yet implemented")
    override val isLegendary: Boolean
        get() = TODO("Not yet implemented")
    override val isMythical: Boolean
        get() = TODO("Not yet implemented")
    override val pokemonCategory: String
        get() = TODO("Not yet implemented")
    override val generation: Int
        get() = TODO("Not yet implemented")
    override val name = species.name

    //@Ignore
    override val hasTwoTypes = secondaryType != PokemonType.NONE
}