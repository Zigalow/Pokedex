package dtu.group21.models.pokemon.moves

import dtu.group21.models.pokemon.PokemonType

interface DisplayMove {
    val name: String
    val power: Int?
    val accuracy: Int?
    val type: PokemonType
}
