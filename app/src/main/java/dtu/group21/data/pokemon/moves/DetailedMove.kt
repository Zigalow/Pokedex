package dtu.group21.data.pokemon.moves

import dtu.group21.data.pokemon.StatPokemon

interface DetailedMove : DisplayMove {
    //    val name: String
    //    val power: Int?
//    val accuracy: Int?
//    val type: PokemonType
    val damageClass: MoveDamageClass
    val pp: Int
    val description: String
    val pokemonTarget: Int? // todo make enums
    val priority: Int
    val makesContact: Boolean
    val generation: Int
    val learntByPokemon: List<StatPokemon>
    val tms: List<String> // todo make generations into enums
}