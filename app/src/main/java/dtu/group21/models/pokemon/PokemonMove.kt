package dtu.group21.models.pokemon

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
