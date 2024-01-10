package dtu.group21.data

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dtu.group21.data.api.PokeAPICo
import dtu.group21.data.api.PokemonAPI
import dtu.group21.data.caches.PokedexCache
import dtu.group21.data.database.AppDatabase
import dtu.group21.models.api.Resource
import dtu.group21.models.pokemon.DetailedPokemon
import dtu.group21.models.pokemon.DisplayPokemon
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

    fun getFavoritePokemons(destination: MutableState<List<Resource<DisplayPokemon>>>) {
        coroutineScope.launch {
            destination.value = database.favoritesDao().getAll().map { Resource.Success(it.getPokemon()) }
        }
    }

    fun getPokemons(pokedexIds: List<Int>, destination: MutableState<List<Resource<DisplayPokemon>>>, cacheResults: Boolean = true) {
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

    private suspend fun getPokemonsInternal(pokedexIds: List<Int>, cacheResults: Boolean): Flow<Resource<List<Resource<DisplayPokemon>>>> = flow {
        val pokemons = mutableListOf<Resource<DisplayPokemon>>()
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

    fun getPokemon(pokedexId: Int, destination: MutableState<DisplayPokemon>, cacheResult: Boolean = true) {
        val cached = PokedexCache.pokemons.firstOrNull { it.pokedexId == pokedexId }
        if (cached != null) {
            destination.value = cached
        }
        else {
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

    private suspend fun getPokemonInternal(pokedexId: Int, cacheResult: Boolean): Flow<Resource<DisplayPokemon>> = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        var retrievedPokemon: DisplayPokemon? = null

        val cached = PokedexCache.pokemons.firstOrNull { it.pokedexId == pokedexId }
        if (cached != null) {
            retrievedPokemon = cached
        }
        else {
            // Database look-up
            val databaseMatches = database.favoritesDao().getPokemonById(pokedexId)
            if (databaseMatches.isNotEmpty()) {
                retrievedPokemon = databaseMatches[0].getPokemon()
            }
            // Fetching online
            else {
                retrievedPokemon = api.getDisplayPokemon(pokedexId)
            }

            if (cacheResult) {
                PokedexCache.addPokemon(retrievedPokemon)
            }
        }

        if (retrievedPokemon != null) {
            emit(Resource.Success(retrievedPokemon))
        }
        else {
            emit(Resource.Failure("Could not retrieve pokemon"))
        }
    }

    fun getDetails(pokedexId: Int, destination: MutableState<DetailedPokemon>, cacheResult: Boolean = true) {
        coroutineScope.launch {
            getDetailsInternal(pokedexId, cacheResult).collect {
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

    private suspend fun getDetailsInternal(pokedexId: Int, cacheResult: Boolean): Flow<Resource<DetailedPokemon>> = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        var retrievedPokemon: DetailedPokemon? = null

        val cached = PokedexCache.details.firstOrNull { it.pokedexId == pokedexId }
        if (cached != null) {
            retrievedPokemon = cached
        }
        else {
            // Database look-up
            val databaseMatches = database.favoritesDao().getPokemonById(pokedexId)
            if (databaseMatches.isNotEmpty()) {
                retrievedPokemon = databaseMatches[0].getPokemon()
            }
            // Fetching online
            else {
                retrievedPokemon = api.getDetailedPokemon(pokedexId)
            }

            if (cacheResult) {
                PokedexCache.addDetails(retrievedPokemon)
            }
        }

        if (retrievedPokemon != null) {
            emit(Resource.Success(retrievedPokemon))
        }
        else {
            emit(Resource.Failure("Could not retrieve pokemon"))
        }
    }
}