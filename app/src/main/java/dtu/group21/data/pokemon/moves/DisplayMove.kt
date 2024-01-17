package dtu.group21.data.pokemon.moves


import dtu.group21.data.pokemon.PokemonType


interface DisplayMove {
    val name: String
    val power: Int?
    val accuracy: Int?
    val pp: Int
    val type: PokemonType
    val damageClass: MoveDamageClass
}
