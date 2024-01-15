package dtu.group21.models.pokemon.moves

import dtu.group21.models.pokemon.MoveDamageClass
import dtu.group21.models.pokemon.PokemonType

interface DisplayMove {
    val name: String
    val power: Int?
    val accuracy: Int?
    val pp: Int
    val type: PokemonType
    val damageClass: MoveDamageClass
}
