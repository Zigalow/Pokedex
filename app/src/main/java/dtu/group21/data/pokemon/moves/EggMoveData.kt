package dtu.group21.data.pokemon.moves

import dtu.group21.data.pokemon.PokemonType

data class EggMoveData(
    override val name: String,
    override val power: Int?,
    override val accuracy: Int?,
    override val pp: Int,
    override val type: PokemonType,
    override val damageClass: MoveDamageClass
):EggMove
