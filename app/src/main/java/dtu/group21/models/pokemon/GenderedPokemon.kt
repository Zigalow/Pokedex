package dtu.group21.models.pokemon

interface GenderedPokemon : DisplayPokemon {

    val gender: PokemonGender
    val hasGenderDifferences: Boolean

}