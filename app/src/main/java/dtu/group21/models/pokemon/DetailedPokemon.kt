package dtu.group21.models.pokemon

import androidx.compose.runtime.MutableState
import dtu.group21.models.pokemon.moves.DisplayMove
import dtu.group21.models.pokemon.moves.LevelMove
import dtu.group21.models.pokemon.moves.MachineMove

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
    val moves: Array<PokemonMove>
    
    
    
    
}