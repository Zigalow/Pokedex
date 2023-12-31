package dtu.group21.models.api

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.EvolutionChainPokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
) : ViewModel() {
    private val pokedexRequestMaker: PokedexRequestMaker = PokedexRequestMaker()

    fun getComplexPokemon(pokedexId: Int, pokemon: MutableState<ComplexPokemon>, emitLoading: Boolean = true) {
        coroutineScope.launch {
            getComplexPokemonInternal(pokedexId).collect {
                val returnedPokemon = when (it) {
                    is Resource.Failure<*> -> ComplexPokemon(
                        -1,
                        PokemonType.NONE,
                        PokemonType.NONE,
                        PokemonGender.MALE,
                        "",
                        emptyArray(),
                        0,
                        0,
                        PokemonStats(0, 0, 0, 0, 0, 0),
                        PokemonSpecies("No pokemon found", 0,false, false, false, false),
                        emptyArray()
                    )

                    is Resource.Success -> it.data
                    Resource.Loading -> ComplexPokemon(
                        0,
                        PokemonType.NONE,
                        PokemonType.NONE,
                        PokemonGender.MALE,
                        "",
                        emptyArray(),
                        0,
                        0,
                        PokemonStats(0, 0, 0, 0, 0, 0),
                        PokemonSpecies("Loading", 0,false, false, false, false),
                        emptyArray()
                    )
                }

                if (emitLoading || returnedPokemon.id != 0) {
                    pokemon.value = returnedPokemon
                }
            }
        }
    }

    private suspend fun getComplexPokemonInternal(pokedexId: Int) = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure<ComplexPokemon>("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)
        val pokemon = pokedexRequestMaker.getComplexPokemon(pokedexId)
        emit(Resource.Success(pokemon))
    }

    fun getPokemons(pokedexIds: Array<Int>, list: MutableList<MutableState<ComplexPokemon>>) {
        for (id in pokedexIds) {
            val mutablePokemon = mutableStateOf(
                ComplexPokemon(
                    0,
                    PokemonType.NONE,
                    PokemonType.NONE,
                    PokemonGender.MALE,
                    "",
                    emptyArray(),
                    0,
                    0,
                    PokemonStats(0, 0, 0, 0, 0, 0),
                    PokemonSpecies("Loading", 0,false, false, false, false),
                    emptyArray()
                )
            )
            list.add(mutablePokemon)
            getComplexPokemon(id, mutablePokemon)
        }
    }

    fun getEvolutionChain(pokedexId: Int, pokemons: MutableState<ArrayList<List<EvolutionChainPokemon>>>) {
        coroutineScope.launch {
            val evolutionChain = ArrayList<List<EvolutionChainPokemon>>()
            getEvolutionChainInternal(pokedexId).collect {
                when (it) {
                    is Resource.Failure<*> -> {
                        return@collect
                    }
                    is Resource.Success -> {
                        evolutionChain.add(it.data)
                    }
                    Resource.Loading -> {
                        // Do nothing
                    }
                }
            }
            println("Coroutine done")
            println("Evolution steps: ${evolutionChain.size}")
            pokemons.value = evolutionChain
        }
    }

    private suspend fun getEvolutionChainInternal(pokedexId: Int) = flow {
        if (pokedexId < 1 || pokedexId > 1010) {
            emit(Resource.Failure<List<EvolutionChainPokemon>>("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        val evolutionChain = ArrayList<ArrayList<EvolutionChainPokemon>>()
        val basePokemon = pokedexRequestMaker.getSimplePokemon(pokedexId)
        evolutionChain.add(arrayListOf(basePokemon))

        // Load previous
        var loadPreviousOf = basePokemon
        while (!loadPreviousOf.isRoot) {
            val previousPokemon = pokedexRequestMaker.getSimplePokemon(loadPreviousOf.precedingPokemonId)
            evolutionChain.add(0, arrayListOf(previousPokemon))
            loadPreviousOf = previousPokemon
        }

        // Load next
        var loadNextOf = basePokemon
        while (loadNextOf.hasEvolutions) {
            val possibilites = ArrayList<EvolutionChainPokemon>()
            for (nextId in loadNextOf.evolutionIds) {
                possibilites.add(pokedexRequestMaker.getSimplePokemon(nextId))
            }
            evolutionChain.add(possibilites)

            // TODO actually handle branching
            break
        }

        // Return all the pokemons
        for (pokemons in evolutionChain) {
            println("Evolution: ${pokemons.size}")
            emit(Resource.Success(pokemons))
        }
    }
}