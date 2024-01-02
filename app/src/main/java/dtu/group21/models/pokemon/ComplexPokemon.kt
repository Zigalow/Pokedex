package dtu.group21.models.pokemon

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

//@Entity(tableName = "favorites")
class ComplexPokemon(
    //@PrimaryKey
    override val pokedexId: Int, // the same as the pokédex number
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    val gender: PokemonGender,
    val categoryName: String,
    val abilities: Array<PokemonAbility>,
    val weightInGrams: Int,
    val heightInCm: Int,
    val stats: PokemonStats,
    val species: PokemonSpecies,
    val moves: Array<PokemonMove>,
    var isFavorite: MutableState<Boolean> = mutableStateOf(false)
) : DisplayPokemon {
    //@Ignore
    override val spriteId = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${
        pokedexId.toString().padStart(3, '0')
    }.png"
    override val name = species.name

    //@Ignore
    override val hasTwoTypes = secondaryType != PokemonType.NONE
}