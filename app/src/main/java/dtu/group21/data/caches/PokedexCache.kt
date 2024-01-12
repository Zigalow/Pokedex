package dtu.group21.data.caches

import dtu.group21.models.pokemon.DetailedPokemon
import dtu.group21.models.pokemon.DisplayPokemon

object PokedexCache {
    val displayCache = PokemonCache<DisplayPokemon>()
    val detailsCache = PokemonCache<DetailedPokemon>()
    val favoritesCache = PokemonCache<DetailedPokemon>()

    val pokemons get() = displayCache.elements + details
    val details get() = detailsCache.elements + favorites
    val favorites get() = favoritesCache.elements

    init {
        // Set initial sizes of the caches
        displayCache.size = 20
        detailsCache.size = 5
        favoritesCache.size = -1 // no limit
    }

    fun addDisplayPokemon(pokemon: DisplayPokemon) {
        // TODO: decide if the equality requirement should be harsher
        //  (e.g. differentiate between different genders of the same pokemon)
        // Don't add it if it's already favorited
        if (pokemon.pokedexId in favoritesCache)
            return

        val detailed = detailsCache[pokemon.pokedexId]
        if (detailed != null) {
            detailsCache.refresh(detailed)
        }
        else {
            displayCache.add(pokemon)
        }
    }

    fun addDetailedPokemon(pokemon: DetailedPokemon) {
        // Don't add it if it's already favorited
        if (pokemon in favoritesCache)
            return

        detailsCache.add(pokemon)
        displayCache.removeIf { it.pokedexId == pokemon.pokedexId }
    }

    fun addFavorite(favorite: DetailedPokemon) {
        displayCache.removeIf { it.pokedexId == favorite.pokedexId }
        detailsCache.removeIf { it == favorite }

        favoritesCache.add(favorite)
    }

    fun removeFavorite(favorite: DetailedPokemon) {
        favoritesCache.removeIf { it == favorite }

        // TODO: decide if it should be moved to the details cache afterwards
    }
}