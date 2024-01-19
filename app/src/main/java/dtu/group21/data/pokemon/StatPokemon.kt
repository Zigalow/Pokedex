package dtu.group21.data.pokemon

interface StatPokemon : DisplayPokemon {
    val stats: PokemonStats

    val total: Int
        get() = stats.hp + stats.attack + stats.defense + stats.specialAttack + stats.specialDefense + stats.speed
}