package dtu.group21.data.pokemon

import dtu.group21.models.pokemon.PokemonGender

interface GenderedPokemon : StatPokemon {

    val gender: PokemonGender
    val hasGenderDifferences: Boolean

}