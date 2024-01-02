package dtu.group21.models.pokemon

interface SpeciesPokemon : DisplayPokemon {

    val evolutionChainId: Int
    val genderRate: Int
    val isBaby: Boolean
    val isLegendary: Boolean
    val isMythical: Boolean
    val pokemonCategory: String
    val generation: Int
    val weightInGrams: Int
    val heightInCm: Int
    val abilities: Array<PokemonAbility>
}