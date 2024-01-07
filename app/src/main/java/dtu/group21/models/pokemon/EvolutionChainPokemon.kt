package dtu.group21.models.pokemon


data class EvolutionChainPokemon(
    val id: Int, // the same as the pokédex number
    val name: String,
    val precedingPokemonId: Int,
    val evolutionIds: IntArray,
) {
    val spriteResourceId = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${
        id.toString().padStart(3, '0')
    }.png"
    val isRoot = precedingPokemonId == -1
    val hasEvolutions = evolutionIds.isNotEmpty()
}