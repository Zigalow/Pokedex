package dtu.group21.data.pokemon

import androidx.compose.runtime.MutableState
import dtu.group21.data.pokemon.moves.DisplayMove

interface DetailedPokemon : SpeciesPokemon {
    var isFavorite: MutableState<Boolean>
    val moves: Array<DisplayMove>
}