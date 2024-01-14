package dtu.group21.data.pokemon

import androidx.compose.runtime.MutableState
import dtu.group21.models.pokemon.PokemonMove
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.moves.BasicMove
import dtu.group21.models.pokemon.moves.DetailedMove
import dtu.group21.models.pokemon.moves.DisplayMove

interface DetailedPokemon : SpeciesPokemon {
    //    val name: String
    //    val pokedexId: Int
    //    val primaryType: PokemonType
    //    val secondaryType: PokemonType
    //    val spriteId: String
    //    val hasTwoTypes: Boolean
    //  val species: SpeciesPokemon
    //    val evolutionChainId: Int
    //    val genderRate: Int
    //    val isBaby: Boolean
    //    val isLegendary: Boolean
    //    val isMythical: Boolean
    //    val pokemonCategory: String
    //    val generation: Int
    //    val weightInGrams: Int
    //    val heightInCm: Int
    //    val abilities: List<PokemonAbility>
    var isFavorite: MutableState<Boolean>
    val moves: Array<DisplayMove>
    val stats: PokemonStats
    
}