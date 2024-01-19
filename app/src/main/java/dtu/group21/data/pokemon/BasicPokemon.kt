package dtu.group21.data.pokemon

data class BasicPokemon(
    override val name: String,
    override val pokedexId: Int,
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    override val spriteId: String,
) : DisplayPokemon {
    override val hasTwoTypes = (secondaryType != PokemonType.NONE)
}
