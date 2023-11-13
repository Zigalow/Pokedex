package dtu.group21.models.pokemon

data class PokemonMove(
    val name: String,
    val description: String,
    val power: Int?,
    val accuracy: Int?,
    val pp: Int,
    val type: PokemonType,
    val damageClass: MoveDamageClass,
    // how will we get this info? I cannot find it in the API
    val learnMethod: MoveLearnMethod,
    val level: Int?,
)
