package dtu.group21.data.pokemon

import dtu.group21.data.pokemon.moves.DisplayMove

interface DetailedPokemon : SpeciesPokemon {
    val moves: Array<DisplayMove>
}