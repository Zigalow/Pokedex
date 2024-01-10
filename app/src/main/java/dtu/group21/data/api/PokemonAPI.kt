package dtu.group21.data.api

import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.models.pokemon.PokemonMove

interface PokemonAPI {
    // Getters for the pokemon
    suspend fun getDisplayPokemon(pokedexId: Int): DisplayPokemon
    suspend fun getDetailedPokemon(pokedexId: Int): DetailedPokemon
    suspend fun getDetailedPokemon(templatePokemon: DisplayPokemon): DetailedPokemon

    // Getters for moves
    suspend fun getMove(moveId: Int): PokemonMove
}