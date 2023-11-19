package dtu.group21.models.pokemon


data class EvolutionChainPokemon(
    val id: Int, // the same as the pokédex number
    val name: String,
    val precedingPokemonId: Int,
    val evolutionIds: IntArray,
) {
    val spriteResourceId = id
    val isRoot = precedingPokemonId == -1
    val hasEvolutions = evolutionIds.isNotEmpty()
}