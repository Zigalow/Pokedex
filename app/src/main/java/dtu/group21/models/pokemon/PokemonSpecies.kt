package dtu.group21.models.pokemon

data class PokemonSpecies(
    val name: String,
    val genderRate: Int,
    val hasGenderDifferences: Boolean,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
)