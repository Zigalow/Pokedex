package dtu.group21.models.pokemon

data class PokemonStats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
) {
    val total = hp + attack + defense + specialAttack + specialDefense + speed

    constructor(stats: List<Int>) : this(stats[0], stats[1], stats[2], stats[3], stats[4], stats[5])
}