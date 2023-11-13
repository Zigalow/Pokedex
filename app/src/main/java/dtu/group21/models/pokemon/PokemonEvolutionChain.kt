package dtu.group21.models.pokemon

class PokemonEvolutionChain(
    val pokemon: Array<SimplePokemon>,
) {
    val hasEvolutions = pokemon.size > 1
}