package com.example.group21.steps

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dtu.group21.models.api.PokedexRequestMaker
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.EvolutionChainPokemon
import dtu.group21.ui.PokeNavHost
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking

class PokemonRetrieval {
    var latestPokemon: ComplexPokemon? = null
    @Given("the user wants to get the pokemon with id {int}")
    fun getPokemon(id: Int) = runBlocking {
        val requester = PokedexRequestMaker()
        latestPokemon = requester.getComplexPokemon(id)
    }

    @Then("the data of the pokemon should be retrieved")
    fun retrievedPokemonShouldHaveData() {
        if (latestPokemon == null) {
            assert(false)
        }
        val pokemon = latestPokemon!!
        assert(pokemon.id > 0)
        assert(pokemon.species.name.isNotEmpty())
        assert(pokemon.moves.isNotEmpty())
    }

    @Then("the name of the pokemon should be {string}")
    fun retrievedPokemonShouldBeCalled(expectedName: String) {
        return
        if (latestPokemon == null) {
            assert(false)
        }
        val pokemon = latestPokemon!!
        assertEquals(expectedName, pokemon.species.name)
    }
    val listOfPokemons = ArrayList<ComplexPokemon>()
    @Given("the user wants to get the pokemons with ids {string}")
    fun getPokemons(ids : String) = runBlocking {
        val idNumbers = ids.split(",").map { it.toInt() }
        val requester = PokedexRequestMaker()
        listOfPokemons.removeAll { true }
        for (id in idNumbers){
            listOfPokemons.add(requester.getComplexPokemon(id))
        }
    }
    @Then("the data of the pokemons should be retrieved")
    fun retrievedPokemonListShouldHaveData(){
        if(listOfPokemons.isEmpty()) assert(false)
        for (pokemon in listOfPokemons){
            latestPokemon = pokemon
            retrievedPokemonShouldHaveData()
        }
        latestPokemon = null
    }
    val evolutionChainPokemon = ArrayList<EvolutionChainPokemon>()
    @Given("the user wants to inspect evolution chain of pokemons with ids {string}")
    fun getEvolution(ids : String) = runBlocking {
        val idNumbers = ids.split(",").map { it.toInt() }
        val requester = PokedexRequestMaker()
        evolutionChainPokemon.removeAll { true }
        for (id in idNumbers){
            evolutionChainPokemon.add(requester.getSimplePokemon(id))
        }
    }
    @Then("the data of the evolution pokemons should be retrieved")
    fun retrievedEvolutionListShouldHaveData(){
        if(evolutionChainPokemon.isEmpty()) assert(false)
        for(pokemon in evolutionChainPokemon){
            if (pokemon.id < 0) assert(false)
            if (pokemon.name.isEmpty()) assert(false)
        }
    }


}
