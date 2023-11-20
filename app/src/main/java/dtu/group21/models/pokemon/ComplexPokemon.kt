package dtu.group21.models.pokemon

class ComplexPokemon(
    val id: Int, // the same as the pokédex number
    val type: PokemonType,
    val secondaryType: PokemonType,
    val gender: PokemonGender,
    val stats: PokemonStats,
    val species: PokemonSpecies,
    val moves: Array<PokemonMove>,
) {
    val spriteResourceId = id
    val hasTwoTypes = secondaryType != PokemonType.NONE
}