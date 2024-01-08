package dtu.group21.data.caches

import dtu.group21.models.pokemon.DisplayPokemon

object PokemonCache {
    const val SIZE = 20

    private val _pokemons = mutableListOf<DisplayPokemon>()
    val pokemons get() = _pokemons.toList()

    fun add(pokemon: DisplayPokemon) {
        if (pokemon in this)
            return

        _pokemons.add(pokemon)
        while (_pokemons.size > SIZE) {
            _pokemons.removeAt(0)
        }
    }

    // Containment operators
    operator fun contains(pokemon: DisplayPokemon) = (pokemon in _pokemons)
    operator fun contains(pokemonId: Int) = _pokemons.any { it.pokedexId == pokemonId }
    fun containsAll(pokemonIds: List<Int>) = pokemonIds.all { it in this }

    // Index operators
    operator fun get(pokemonId: Int) = _pokemons.firstOrNull { it.pokedexId == pokemonId }
}