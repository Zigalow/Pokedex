package dtu.group21.models.pokemon

interface DisplayPokemon {

    val name: String
    val pokedexId: Int
    val primaryType: PokemonType
    val secondaryType: PokemonType
    val spriteId: String
    val hasTwoTypes: Boolean
}