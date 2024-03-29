package dtu.group21.data.pokemon.moves


import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.data.pokemon.PokemonType



data class AdvancedMove(
    override val damageClass: MoveDamageClass,
    override val pp: Int,
    override val description: String,
    override val pokemonTarget: Int?,
    override val priority: Int,
    override val generation: Int,
    override val learntByPokemon: List<DisplayPokemon>,
    override val tms: List<String>,
    override val name: String,
    override val power: Int?,
    override val accuracy: Int?,
    override val type: PokemonType
): DetailedMove
