package dtu.group21.models.pokemon.moves

import dtu.group21.models.pokemon.MoveDamageClass
import dtu.group21.models.pokemon.PokemonType

data class EggMoveData(
    override val name: String,
    override val power: Int?,
    override val accuracy: Int?,
    override val pp: Int,
    override val type: PokemonType,
    override val damageClass: MoveDamageClass
):EggMove
