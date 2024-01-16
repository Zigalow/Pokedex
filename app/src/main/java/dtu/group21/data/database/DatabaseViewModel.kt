package dtu.group21.data.database

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dtu.group21.data.Resource
import dtu.group21.models.api.PokemonViewModel

import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.moves.LevelMove
import dtu.group21.data.pokemon.moves.MachineMove

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DatabaseViewModel(
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
) : ViewModel() {
    val requestViewModel = PokemonViewModel()

    private fun complexPokemonToPokemonData(pokemon: ComplexPokemon): PokemonData {
        val abilitiesStringBuilder = StringBuilder()
        for (ability in pokemon.abilities) {
            abilitiesStringBuilder.append(ability.name)
            abilitiesStringBuilder.append(';')
            abilitiesStringBuilder.append(ability.description)
            abilitiesStringBuilder.append(';')
            abilitiesStringBuilder.append(ability.isHidden.toString())

            // for the next ability
            abilitiesStringBuilder.append("::")
        }
        abilitiesStringBuilder.setLength(abilitiesStringBuilder.length - 2)

        val stats = pokemon.stats
        val statsString = "${stats.hp};${stats.attack};${stats.defense};${stats.specialAttack};${stats.specialDefense};${stats.speed}"
        val species = pokemon.species
        val speciesString = "${species.name};${species.genderRate};${species.hasGenderDifferences};${species.isBaby};${species.isLegendary};${species.isMythical}"

        val movesStringBuilder = StringBuilder()
        for (move in pokemon.moves) {
            movesStringBuilder.append(move.name)
            movesStringBuilder.append(';')
            //movesStringBuilder.append(/*move.description TODO*/ "For now")
            //movesStringBuilder.append(';')
            movesStringBuilder.append(move.power.toString())
            movesStringBuilder.append(';')
            movesStringBuilder.append(move.accuracy.toString())
            movesStringBuilder.append(';')
            movesStringBuilder.append(move.pp.toString())
            movesStringBuilder.append(';')
            movesStringBuilder.append(move.type.toString())
            movesStringBuilder.append(';')
            movesStringBuilder.append(move.damageClass.toString())

            if(move is LevelMove){
                movesStringBuilder.append(';')
                movesStringBuilder.append(move.level.toString())
            }
            if(move is MachineMove){
                movesStringBuilder.append(';')
                movesStringBuilder.append(move.machineId)
            }

            // for the next move
            movesStringBuilder.append("::")
        }
        movesStringBuilder.setLength(movesStringBuilder.length - 2)

        return PokemonData(
            pokemon.pokedexId,
            pokemon.primaryType.toString(),
            pokemon.secondaryType.toString(),
            pokemon.gender.toString(),
            pokemon.category,
            abilitiesStringBuilder.toString(),
            pokemon.weightInGrams.toString(),
            pokemon.heightInCm.toString(),
            statsString,
            speciesString,
            movesStringBuilder.toString()
        )
    }

    /*
    fun getPokemons(pokemons: MutableState<ArrayList<MutableState<DisplayPokemon>>>, database: AppDatabase) {
        coroutineScope.launch {
            val pokemonList: ArrayList<MutableState<DisplayPokemon>> = ArrayList()
            getPokemonsInternal(database).collect {
                when (it) {
                    is Resource.Success -> {
                        val pokemon = it.data.getPokemon()
                        pokemon.isFavorite.value = true
                        pokemonList.add(mutableStateOf(pokemon))
                    }
                    is Resource.Failure<*> -> {
                        return@collect
                    }
                    Resource.Loading -> {
                        // Do nothing
                    }
                }
            }
            pokemons.value = pokemonList
        }
    }

    private suspend fun getPokemonsInternal(database: AppDatabase) = flow {
        emit(Resource.Loading)

        val matchingPokemons = database.pokemonDao().getAll()
        if (matchingPokemons.isEmpty()) {
            emit(Resource.Failure<ComplexPokemon>("No pokemon"))
        }
        else {
            for (pokemon in matchingPokemons) {
                emit(Resource.Success(pokemon))
            }
        }
    }
    */

    fun getPokemon(pokedexId: Int, pokemon: MutableState<DetailedPokemon>, database: AppDatabase) {
        coroutineScope.launch {
            getPokemonInternal(pokedexId, database).collect {
                when (it) {
                    is Resource.Success -> {
                        val favoritePokemon = it.data
                        favoritePokemon.isFavorite.value = true
                        pokemon.value = favoritePokemon
                    }
                    is Resource.Failure -> {
                        // Make the network request
                      //  requestViewModel.getComplexPokemon(pokedexId, pokemon)
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
            emit(Resource.Failure("Number not valid"))
            return@flow
        }
        emit(Resource.Loading)

        val matchingPokemons = database.favoritesDao().getPokemonById(pokedexId)
        if (matchingPokemons.isNotEmpty()) {
            val pokemon = matchingPokemons[0].getPokemon()
            pokemon.isFavorite.value = true
            emit(Resource.Success(pokemon))
        }
        else {
            emit(Resource.Failure("Not in the database"))
        }
    }

    fun insertPokemon(pokemon: ComplexPokemon, database: AppDatabase) {
        coroutineScope.launch {
            val pokemonData = complexPokemonToPokemonData(pokemon)
            database.favoritesDao().insertAll(pokemonData)
        }
    }

    fun deletePokemon(pokemon: ComplexPokemon, database: AppDatabase) {
        coroutineScope.launch {
            val pokemonData = complexPokemonToPokemonData(pokemon)
            database.favoritesDao().delete(pokemonData)
        }
    }
}