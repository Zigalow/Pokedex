package dtu.group21.data.pokemon

import dtu.group21.data.pokemon.moves.PokemonMove

interface DetailedPokemon : SpeciesPokemon {
    val moves: Array<PokemonMove>
}