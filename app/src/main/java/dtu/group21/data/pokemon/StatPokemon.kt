package dtu.group21.data.pokemon

interface StatPokemon : DisplayPokemon {

    val hp: Int
    val attack: Int
    val defense: Int
    val specialAttack: Int
    val specialDefense: Int
    val speed: Int
    val total: Int
        get() = hp + attack + defense + specialAttack + specialDefense + speed
    
}