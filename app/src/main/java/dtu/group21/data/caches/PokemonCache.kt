package dtu.group21.data.caches

import dtu.group21.data.pokemon.DisplayPokemon

class PokemonCache<T : DisplayPokemon> : Cache<T>() {

    // Access operators
    operator fun get(pokemonId: Int) = this._elements.firstOrNull { it.pokedexId == pokemonId }
    operator fun contains(pokemonId: Int) = this._elements.any { it.pokedexId == pokemonId }
}