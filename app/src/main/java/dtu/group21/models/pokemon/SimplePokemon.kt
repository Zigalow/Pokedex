package dtu.group21.models.pokemon


data class SimplePokemon(
    val id: Int, // the same as the pokédex number
    val name: String,
    val spriteResourceId: Int,
)