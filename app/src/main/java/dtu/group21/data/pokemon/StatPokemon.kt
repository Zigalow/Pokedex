package dtu.group21.data.pokemon

import dtu.group21.models.pokemon.PokemonStats

interface StatPokemon : DisplayPokemon {

    val stats: PokemonStats
    val total: Int
        get() = stats.hp + stats.attack + stats.defense + stats.specialAttack + stats.specialDefense + stats.speed

}