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
}