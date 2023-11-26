package dtu.group21.models.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType


@Entity(tableName = "pokemons")
data class PokemonData(
    @PrimaryKey
    val id: Int,
    val name: String,
    val primaryTypeString: String,
    val secondaryTypeString: String,
    val genderString: String,
    val categoryName: String,
    // abilities
    val weightInGrams: String,
    val heightInCm: String,
    // stats
    // species
    // moves
) {
    @Ignore
    val primaryType = PokemonType.getFromName(primaryTypeString)

    @Ignore
    val secondaryType = PokemonType.getFromName(secondaryTypeString)

    fun getSimplifiedPokemon(): ComplexPokemon {
        val gender = when (genderString.lowercase()) {
            "male" -> PokemonGender.MALE
            "female" -> PokemonGender.FEMALE
            else -> PokemonGender.GENDERLESS
        }
        return ComplexPokemon(
            id,
            primaryType,
            secondaryType,
            gender,
            categoryName,
            emptyArray(),
            weightInGrams.toInt(),
            heightInCm.toInt(),
            PokemonStats(
                0,0,0,0,0,0
            ),
            PokemonSpecies(
                name,
                8,
                false,false,false,false
            ),
            emptyArray()
        )
    }
}