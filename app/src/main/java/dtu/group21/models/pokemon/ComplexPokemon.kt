package dtu.group21.models.pokemon

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

//@Entity(tableName = "favorites")
data class ComplexPokemon(
    //@PrimaryKey
    val id: Int, // the same as the pokédex number
    val type: PokemonType,
    val secondaryType: PokemonType,
    val gender: PokemonGender,
    val categoryName: String,
    val abilities: Array<PokemonAbility>,
    val weightInGrams: Int,
    val heightInCm: Int,
    val stats: PokemonStats,
    val species: PokemonSpecies,
    val moves: Array<PokemonMove>,
) {
    //@Ignore
    val spriteResourceId = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${
        id.toString().padStart(3, '0')
    }.png"
    //@Ignore
    val hasTwoTypes = secondaryType != PokemonType.NONE
}