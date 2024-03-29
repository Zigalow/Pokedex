package dtu.group21.data.pokemon

interface SpeciesPokemon : StatPokemon {
    val evolutionChainId: Int
    val genderRate: Int
    val isBaby: Boolean
    val isLegendary: Boolean
    val isMythical: Boolean
    val category: String
    val generation: Int
    val weightInGrams: Int
    val heightInCm: Int
    val abilities: Array<PokemonAbility>
}