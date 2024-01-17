package dtu.group21.data.pokemon

data class BasicStatPokemon(
    override val name: String,
    override val pokedexId: Int,
    override val primaryType: PokemonType,
    override val secondaryType: PokemonType,
    override val spriteId: String,
    override val stats: PokemonStats
) : StatPokemon {
    override val hasTwoTypes = (secondaryType != PokemonType.NONE)
}
