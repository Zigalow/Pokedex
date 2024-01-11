package dtu.group21.data.caches

import dtu.group21.models.pokemon.DetailedPokemon
import dtu.group21.models.pokemon.DisplayPokemon

object PokedexCache {
    val displayCache = Cache<DisplayPokemon>()
    val detailsCache = Cache<DetailedPokemon>()

    val pokemons get() = displayCache.elements + details
    val details get() = detailsCache.elements

    init {
        // Set initial sizes of the caches
        displayCache.size = 20
        detailsCache.size = 5
    }

    fun addDisplayPokemon(pokemon: DisplayPokemon) {
        val detailed = detailsCache.elements.firstOrNull { it.pokedexId == pokemon.pokedexId }
        if (detailed != null) {
            // Refresh it in the cache
            detailsCache.add(detailed)
        }
        else {
            displayCache.add(pokemon)
        }
    }

    fun addDetailedPokemon(pokemon: DetailedPokemon) {
        detailsCache.add(pokemon)
    }
}