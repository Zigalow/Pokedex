package dtu.group21.models.api

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class PokemonViewModel(
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
) : ViewModel() {
    private val pokedexRequestMaker: PokedexRequestMaker = PokedexRequestMaker()

    fun getPokemon(pokedexId: Int, pokemon: MutableState<ComplexPokemon>) {
        coroutineScope.launch {
            getPokemonInternal(pokedexId).collect {
                pokemon.value = when (it) {
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
            }
        }
    }

    private suspend fun getPokemonInternal(pokedexId: Int) = flow {
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
            getPokemon(id, mutablePokemon)
        }
    }
}