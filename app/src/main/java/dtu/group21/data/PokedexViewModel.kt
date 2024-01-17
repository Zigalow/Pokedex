package dtu.group21.data

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dtu.group21.data.api.PokeAPICo
import dtu.group21.data.api.PokemonAPI
import dtu.group21.data.caches.PokedexCache
import dtu.group21.data.database.AppDatabase
import dtu.group21.data.database.FavoriteData
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.data.pokemon.StatPokemon
import dtu.group21.pokedex.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PokedexViewModel(
    private val api: PokemonAPI = PokeAPICo()
) : ViewModel() {
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val database: AppDatabase = MainActivity.database

    fun getFavoritePokemons(destination: MutableState<List<Resource<StatPokemon>>>) {
        coroutineScope.launch {
            destination.value =
                database.favoritesDao().getAll().map { Resource.Success(it.toPokemon()) }
        }
    }

    fun makeFavorite(pokemon: DetailedPokemon) {
        coroutineScope.launch {
            database.favoritesDao().insertAll(FavoriteData(pokemon))
        }
    }

    fun removeFavorite(pokemon: DetailedPokemon) {
        coroutineScope.launch {
            database.favoritesDao().delete(FavoriteData(pokemon))
        }
    }

    fun getPokemons(
        pokedexIds: List<Int>,
        destination: MutableState<List<Resource<StatPokemon>>>,
        cacheResults: Boolean = true
    ) {
        coroutineScope.launch {
            getPokemonsInternal(pokedexIds).collect {
                when (it) {
                    is Resource.Success -> {
                        destination.value = it.data
                    }

                    is Resource.Failure -> {
                        return@collect
                    }

                    Resource.Loading -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    private suspend fun getPokemonsInternal(pokedexIds: List<Int>): Flow<Resource<List<Resource<StatPokemon>>>> = flow {
        val pokemons = mutableMapOf<Int, Resource<StatPokemon>>()
        pokedexIds.forEach { pokemons[it] = Resource.Loading }

        // TODO: probably make this better
        fun getPokemonList(map: Map<Int, Resource<StatPokemon>>, order: List<Int>): List<Resource<StatPokemon>> {
            return order.map { map[it]!! }
        }

        val leftToLoad = pokedexIds.toMutableList()

        println("Looking for matches in the cache in $pokedexIds")
        println("Cache content: ${PokedexCache.pokemons.map { it.pokedexId }}")
        // Check for cache hits
        PokedexCache.pokemons.forEach { cached ->
            if (cached.pokedexId in pokedexIds) {
                pokemons[cached.pokedexId] = Resource.Success(cached)
                leftToLoad.remove(cached.pokedexId)
            }
        }
        emit(Resource.Success(getPokemonList(pokemons, pokedexIds)))

        leftToLoad.forEachIndexed { i, id ->
            getPokemonInternal(id, true).collect {
                when (it) {
                    is Resource.Success -> {
                        val pokemon = it.data
                        pokemons[pokemon.pokedexId] = Resource.Success(pokemon)
                        emit(Resource.Success(getPokemonList(pokemons, pokedexIds)))
                    }

                    is Resource.Failure -> {
                        // TODO handle?
                        emit(Resource.Failure("TODO"))
                    }

                    Resource.Loading -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    fun getPokemon(
        pokedexId: Int,
        destination: MutableState<Resource<DisplayPokemon>>,
        cacheResult: Boolean = true
    ) {
        val cached = PokedexCache.pokemons.firstOrNull { it.pokedexId == pokedexId }
        if (cached != null) {
            destination.value = Resource.Success(cached)
        } else {
            coroutineScope.launch {
                getPokemonInternal(pokedexId, cacheResult).collect {
                    when (it) {
                        is Resource.Success -> {
                            destination.value = Resource.Success(it.data)
                        }

                        is Resource.Failure -> {
                            // TODO handle failure if needed
                        }

                        Resource.Loading -> {
                            // Do nothing or handle loading state if needed
                        }
                    }
                }
            }
        }
    }


    private suspend fun getPokemonInternal(
        pokedexId: Int,
        cacheResult: Boolean
    ): Flow<Resource<StatPokemon>> = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        var retrievedPokemon: StatPokemon? = PokedexCache.pokemons.firstOrNull { it.pokedexId == pokedexId }
        if (retrievedPokemon == null) {
            // Database look-up
            val databaseMatches = database.favoritesDao().getPokemonById(pokedexId)
            if (databaseMatches.isNotEmpty()) {
                retrievedPokemon = databaseMatches[0].toPokemon()
            }
            // Fetching online
            else {
                retrievedPokemon = api.getStatPokemon(pokedexId)
            }

            if (cacheResult) {
                PokedexCache.addDisplayPokemon(retrievedPokemon)
            }
        }

        if (retrievedPokemon != null) {
            emit(Resource.Success(retrievedPokemon))
        } else {
            emit(Resource.Failure("Could not retrieve pokemon"))
        }
    }

    fun getDetails(
        pokedexId: Int,
        destination: MutableState<Pair<Resource<DetailedPokemon>, Boolean>>,
        cacheResult: Boolean = true
    ) {
        coroutineScope.launch {
            val isFavorite = database.favoritesDao().getAll().any { it.id == pokedexId }

            getDetailsInternal(pokedexId, cacheResult).collect {
                destination.value = Pair(it, isFavorite)
            }
        }
    }

    private suspend fun getDetailsInternal(pokedexId: Int, cacheResult: Boolean): Flow<Resource<DetailedPokemon>> = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        var retrievedPokemon: DetailedPokemon? = PokedexCache.details.firstOrNull { it.pokedexId == pokedexId }
        if (retrievedPokemon == null) {
            // Database look-up
            val databaseMatches = database.favoritesDao().getPokemonById(pokedexId)
            if (databaseMatches.isNotEmpty()) {
                retrievedPokemon = databaseMatches[0].toPokemon()
            }
            // Fetching online
            else {
                retrievedPokemon = api.getDetailedPokemon(pokedexId)
            }

            if (cacheResult) {
                PokedexCache.addDetailedPokemon(retrievedPokemon)
            }
        }

        if (retrievedPokemon != null) {
            emit(Resource.Success(retrievedPokemon))
        } else {
            emit(Resource.Failure("Could not retrieve pokemon"))
        }
    }
}