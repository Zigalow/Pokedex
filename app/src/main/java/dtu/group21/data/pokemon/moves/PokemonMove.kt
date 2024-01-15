package dtu.group21.data.pokemon.moves

import dtu.group21.data.pokemon.PokemonType

data class PokemonMove(
    val name: String,
    val description: String,
    val power: Int?,
    val accuracy: Int?,
    val pp: Int,
    val type: PokemonType,
    val damageClass: MoveDamageClass,

    // TODO: can only be found on a per-pokemon basis
    //val learnMethod: MoveLearnMethod,
    //val level: Int?,
)
