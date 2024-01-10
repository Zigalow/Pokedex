package dtu.group21.data.caches

import dtu.group21.models.pokemon.DetailedPokemon
import dtu.group21.models.pokemon.DisplayPokemon
import dtu.group21.models.pokemon.PokemonMove

object PokedexCache {
    private const val POKEMON_COUNT = 20
    private const val DETAILS_COUNT = 5
    private const val MOVE_COUNT = 50

    private val _pokemons = mutableListOf<DisplayPokemon>()
    private val _details = mutableListOf<DetailedPokemon>()
    private val _moves = mutableListOf<PokemonMove>()

    val pokemons get() = _pokemons.toList() + _details.toList()
    val details get() = _details.toList()
    val moves get() = _moves.toList()

    private fun <T> add(item: T, list: MutableList<T>, maxSize: Int) {
        if (item in list)
            return

        list.add(item)
        if (list.size > maxSize) {
            // Remove the oldest cache
            list.removeAt(0)
        }
    }

    fun addPokemon(pokemon: DisplayPokemon) {
        // If it is already cached at a higher resolution
        if (_details.any { it.pokedexId == pokemon.pokedexId }) {
            return
        }
        add(pokemon, _pokemons, POKEMON_COUNT)
    }
    fun addDetails(pokemon: DetailedPokemon) {
        add(pokemon, _details, DETAILS_COUNT)
        // Remove it from the lower resolution cache
        _pokemons.removeIf { it.pokedexId == pokemon.pokedexId }
    }
    fun addMove(move: PokemonMove) { add(move, _moves, MOVE_COUNT) }
}