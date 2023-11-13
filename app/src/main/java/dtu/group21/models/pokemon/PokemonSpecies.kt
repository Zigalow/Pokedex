package dtu.group21.models.pokemon

data class PokemonSpecies(
    val name: String,
    val hasGenderDifferences: Boolean,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val evolutionChain: PokemonEvolutionChain,
)