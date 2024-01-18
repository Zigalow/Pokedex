package dtu.group21.models.api

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dtu.group21.data.Resource
import dtu.group21.models.pokemon.EvolutionChainPokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

object PokemonViewModel : ViewModel() {
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val pokedexRequestMaker: PokedexRequestMaker = PokedexRequestMaker()

    fun getEvolutionChain(
        pokedexId: Int,
        pokemons: MutableState<ArrayList<List<EvolutionChainPokemon>>>
    ) {
        coroutineScope.launch {
            val evolutionChain = ArrayList<List<EvolutionChainPokemon>>()
            getEvolutionChainInternal(pokedexId).collect {
                when (it) {
                    is Resource.Failure -> {
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
            emit(Resource.Failure("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        val evolutionChain = ArrayList<ArrayList<EvolutionChainPokemon>>()
        println("Base pokemon")
        val basePokemon = pokedexRequestMaker.getSimplePokemon(pokedexId)
        evolutionChain.add(arrayListOf(basePokemon))
        for (i in evolutionChain) {
            println("Current i: ${i}")
            for (j in i) {
                println("Id: ${j.id}")
            }
        }

        // Load previous
        var loadPreviousOf = basePokemon
        while (!loadPreviousOf.isRoot) {
            println("PreviousPokemon")
            val previousPokemon =
                pokedexRequestMaker.getSimplePokemon(loadPreviousOf.precedingPokemonId)
            evolutionChain.add(0, arrayListOf(previousPokemon))
            for (i in evolutionChain) {
                println("Current i: ${i}")
                for (j in i) {
                    println("Id: ${j.id}")
                }
            }
            loadPreviousOf = previousPokemon
        }

        // Bug was present in the below section. It would not work, if the first
        // Load next
        var loadNextOf = basePokemon
        while (loadNextOf.hasEvolutions) {
            println("NextPokemon")
            val possibilites = ArrayList<EvolutionChainPokemon>()
            for (nextId in loadNextOf.evolutionIds) {
                possibilites.add(pokedexRequestMaker.getSimplePokemon(nextId))
            }
            if (possibilites.size < 3) {
                evolutionChain.add(arrayListOf(possibilites.first()))
                loadNextOf = possibilites.removeFirst()
            } else {
                evolutionChain.add(possibilites)
                break
            }

            // TODO actually handle branching
//            break
        }

        // Return all the pokemons
        for (pokemons in evolutionChain) {
            println("Evolution: ${pokemons.size}")
            emit(Resource.Success(pokemons))
        }
    }
}