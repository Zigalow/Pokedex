package dtu.group21.models.pokemon

data class PreviewPokemon(
    val id: Int, // the same as the pokédex number
    val name: String,
    val type: PokemonType,
    val secondaryType: PokemonType,
    val spriteResourceId: Int,
) {
    val hasTwoTypes = secondaryType != PokemonType.NONE
}