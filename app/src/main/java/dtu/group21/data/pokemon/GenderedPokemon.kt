package dtu.group21.data.pokemon

interface GenderedPokemon : StatPokemon {

    val gender: PokemonGender
    val hasGenderDifferences: Boolean
}