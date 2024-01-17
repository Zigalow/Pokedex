package dtu.group21.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import dtu.group21.data.pokemon.AdvancedPokemon
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.PokemonAbility
import dtu.group21.data.pokemon.PokemonStats
import dtu.group21.data.pokemon.PokemonType
import dtu.group21.data.pokemon.moves.DisplayMove
import dtu.group21.data.pokemon.moves.PokemonMove

@Entity(tableName = "favorites")
data class FavoriteData(
    @PrimaryKey val id: Int,
    val name: String,
    val moves: Array<DisplayMove>,
    val primaryType: PokemonType,
    val secondaryType: PokemonType,
    val spriteId: String,
    val evolutionChainId: Int,
    val genderRate: Int,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val category: String,
    val generation: Int,
    val weightInGrams: Int,
    val heightInCm: Int,
    val abilities: Array<PokemonAbility>,
    val stats: PokemonStats
) {
    constructor(pokemon: DetailedPokemon) : this(
        id = pokemon.pokedexId,
        name = pokemon.name,
        moves = pokemon.moves,
        primaryType = pokemon.primaryType,
        secondaryType = pokemon.secondaryType,
        spriteId = pokemon.spriteId,
        evolutionChainId = pokemon.evolutionChainId,
        genderRate = pokemon.genderRate,
        isBaby = pokemon.isBaby,
        isLegendary = pokemon.isLegendary,
        isMythical = pokemon.isMythical,
        category = pokemon.category,
        generation = pokemon.generation,
        weightInGrams = pokemon.weightInGrams,
        heightInCm = pokemon.heightInCm,
        abilities = pokemon.abilities,
        stats = pokemon.stats,
    )

    fun toPokemon(): DetailedPokemon {
        return AdvancedPokemon(
            name = name,
            pokedexId = id,
            primaryType = primaryType,
            secondaryType = secondaryType,
            moves = moves,
            stats = stats,
            evolutionChainId = evolutionChainId,
            genderRate = genderRate,
            isBaby = isBaby,
            isLegendary = isLegendary,
            isMythical = isMythical,
            category = category,
            generation = generation,
            weightInGrams = weightInGrams,
            heightInCm = heightInCm,
            abilities = abilities,
            spriteId = spriteId,
        )
    }
}
