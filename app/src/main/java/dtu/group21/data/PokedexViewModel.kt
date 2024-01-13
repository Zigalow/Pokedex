package dtu.group21.data

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dtu.group21.data.api.PokeAPICo
import dtu.group21.data.api.PokemonAPI
import dtu.group21.data.caches.PokemonCache
import dtu.group21.data.database.AppDatabase
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.data.pokemon.StatPokemon
import dtu.group21.models.api.Resource
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
    private val database: AppDatabase = MainActivity.database!!

    fun isFavoritePokemon(pokedexId: Int): Boolean {
        // TODO: is this even allowed?
        return database.favoritesDao().getAll().any { it.id == pokedexId }
    }

    fun getFavoritePokemons(destination: MutableState<List<Resource<StatPokemon>>>) {
        coroutineScope.launch {
            destination.value =
                database.favoritesDao().getAll().map { Resource.Success(it.getPokemon()) }
        }
    }

    fun getPokemons(
        pokedexIds: List<Int>,
        destination: MutableState<List<Resource<StatPokemon>>>,
        cacheResults: Boolean = true
    ) {
        coroutineScope.launch {
            getPokemonsInternal(pokedexIds, cacheResults).collect {
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

    private suspend fun getPokemonsInternal(
        pokedexIds: List<Int>,
        cacheResults: Boolean
    ): Flow<Resource<List<Resource<StatPokemon>>>> = flow {
        val pokemons = mutableListOf<Resource<StatPokemon>>()
        pokedexIds.forEach { pokemons.add(Resource.Loading) }
        emit(Resource.Success(pokemons.toList()))

        pokedexIds.forEachIndexed { i, id ->
            getPokemonInternal(id, cacheResults).collect {
                when (it) {
                    is Resource.Success -> {
                        pokemons.removeAt(i)
                        pokemons.add(i, Resource.Success(it.data))
                        emit(Resource.Success(pokemons.toList()))
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
        destination: MutableState<DisplayPokemon>,
        cacheResult: Boolean = true
    ) {
        if (pokedexId in PokemonCache) {
            destination.value = PokemonCache[pokedexId]!!
        } else {
            coroutineScope.launch {
                getPokemonInternal(pokedexId, cacheResult).collect {
                    when (it) {
                        is Resource.Success -> {
                            destination.value = it.data
                        }

                        is Resource.Failure -> {
                            // TODO handle?
                        }

                        Resource.Loading -> {
                            // Do nothing
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

        var retrievedPokemon: StatPokemon? = null

        if (pokedexId in PokemonCache) {
            retrievedPokemon = PokemonCache[pokedexId]
        } else {
            // Database look-up
            val databaseMatches = database.favoritesDao().getPokemonById(pokedexId)
            if (databaseMatches.isNotEmpty()) {
                retrievedPokemon = databaseMatches[0].getPokemon()
            }
            // Fetching online
            else {
                retrievedPokemon = api.getStatPokemon(pokedexId)
            }

            if (cacheResult) {
                PokemonCache.add(retrievedPokemon)
            }
        }

        if (retrievedPokemon != null) {
            emit(Resource.Success(retrievedPokemon))
        } else {
            emit(Resource.Failure("Could not retrieve pokemon"))
        }
    }

    fun getDetails(pokedexId: Int, destination: MutableState<DetailedPokemon>) {
        coroutineScope.launch {
            getDetailsInternal(pokedexId).collect {
                when (it) {
                    is Resource.Success -> {
                        destination.value = it.data
                    }

                    is Resource.Failure -> {
                        // TODO handle?
                    }

                    Resource.Loading -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    private suspend fun getDetailsInternal(pokedexId: Int): Flow<Resource<DetailedPokemon>> = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        var retrievedPokemon: DetailedPokemon? = null

        // TODO: make a cache for the details as well
        if (pokedexId in PokemonCache && false) {
            // retrievedPokemon = PokemonCache[pokedexId]
        } else {
            // Database look-up
            val databaseMatches = database.favoritesDao().getPokemonById(pokedexId)
            if (databaseMatches.isNotEmpty()) {
                retrievedPokemon = databaseMatches[0].getPokemon()
            }
            // Fetching online
            else {
                retrievedPokemon = api.getDetailedPokemon(pokedexId)
            }
        }

        if (retrievedPokemon != null) {
            emit(Resource.Success(retrievedPokemon))
        } else {
            emit(Resource.Failure("Could not retrieve pokemon"))
        }
    }
}