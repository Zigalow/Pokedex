package dtu.group21.models.pokemon.moves

import dtu.group21.data.pokemon.PokemonType
import dtu.group21.data.pokemon.moves.DisplayMove
import dtu.group21.data.pokemon.moves.MoveDamageClass

data class BasicMove(
    override val name: String,
    override val power: Int?,
    override val accuracy: Int?,
    override val pp: Int,
    override val type: PokemonType,
    override val damageClass: MoveDamageClass
): DisplayMove
