package dtu.group21.models.database

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dtu.group21.models.api.PokemonViewModel
import dtu.group21.models.api.Resource
import dtu.group21.models.pokemon.ComplexPokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DatabaseViewModel(
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
) : ViewModel() {
    val requestViewModel = PokemonViewModel()

    fun getPokemon(pokedexId: Int, pokemon: MutableState<ComplexPokemon>, database: AppDatabase) {
        coroutineScope.launch {
            getPokemonInternal(pokedexId, database).collect {
                when (it) {
                    is Resource.Success -> {
                        pokemon.value = it.data
                        // Make the network request (without loading)
                        requestViewModel.getComplexPokemon(pokedexId, pokemon, false)
                    }
                    is Resource.Failure<*> -> {
                        // Make the network request
                        requestViewModel.getComplexPokemon(pokedexId, pokemon)
                    }
                    Resource.Loading -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    private suspend fun getPokemonInternal(pokedexId: Int, database: AppDatabase) = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure<ComplexPokemon>("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        val matchingPokemons = database.pokemonDao().getPokemonById(pokedexId)
        if (matchingPokemons.isNotEmpty()) {
            emit(Resource.Success(matchingPokemons[0].getSimplifiedPokemon()))
        }
        else {
            emit(Resource.Failure<ComplexPokemon>("Not in the database"))
        }
    }

    fun insertPokemon(pokemon: ComplexPokemon, database: AppDatabase) {
        coroutineScope.launch {
            val pokemonData = PokemonData(
                pokemon.id,
                pokemon.species.name,
                pokemon.type.toString(),
                pokemon.secondaryType.toString(),
                pokemon.gender.toString(),
                pokemon.categoryName,
                pokemon.weightInGrams.toString(),
                pokemon.heightInCm.toString()
            )
            database.pokemonDao().insertAll(pokemonData)
        }
    }
}