package dtu.group21.models.pokemon

data class PokemonMove(
    val name: String,
    val moveEffectCategory: MoveEffectCategory,
    val category: MoveCategory,
    val type: PokemonType,
    val power: Int?,
    val accuracy: Int?,
    val pp: Int,
    val description: String,
    val level: Int?,
    )
