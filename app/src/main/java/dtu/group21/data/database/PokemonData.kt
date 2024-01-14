package dtu.group21.data.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.PokemonAbility
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.models.pokemon.moves.BasicMove
import dtu.group21.models.pokemon.moves.DisplayMove


@Entity(tableName = "pokemons")
data class PokemonData(
    @PrimaryKey
    val id: Int,
    val primaryTypeString: String,
    val secondaryTypeString: String,
    val genderString: String,
    val categoryName: String,
    val abilitiesString: String,
    val weightInGrams: String,
    val heightInCm: String,
    val statsString: String,
    val speciesString: String,
    val movesString: String
) {
    @Ignore
    val primaryType = PokemonType.getFromName(primaryTypeString)

    @Ignore
    val secondaryType = PokemonType.getFromName(secondaryTypeString)

    fun getPokemon(): ComplexPokemon {
        val abilityStrings = abilitiesString.split("::")
        val abilityArray = Array(abilityStrings.size) {
            val abilityStringParts = abilityStrings[it].split(";")

            PokemonAbility(
                abilityStringParts[0],
                abilityStringParts[1],
                abilityStringParts[2].toBoolean(),
            )
        }

        val gender = when (genderString.lowercase()) {
            "male" -> PokemonGender.MALE
            "female" -> PokemonGender.FEMALE
            else -> PokemonGender.GENDERLESS
        }
        val statsParts = statsString.split(";")
        val stats = PokemonStats(
            statsParts[0].toInt(),
            statsParts[1].toInt(),
            statsParts[2].toInt(),
            statsParts[3].toInt(),
            statsParts[4].toInt(),
            statsParts[5].toInt(),
        )
        val speciesParts = speciesString.split(";")
        val species = PokemonSpecies(
            speciesParts[0],
            speciesParts[1].toInt(),
            speciesParts[2].toBoolean(),
            speciesParts[3].toBoolean(),
            speciesParts[4].toBoolean(),
            speciesParts[5].toBoolean(),
        )

        val moveStrings = movesString.split("::")
        val movesArray: Array<DisplayMove> = Array(moveStrings.size) {
            val moveStringParts = moveStrings[it].split(";")
             BasicMove(
                name = moveStringParts[0],
                power = moveStringParts[1].toIntOrNull(),
                accuracy = moveStringParts[2].toIntOrNull(),
                type = PokemonType.getFromName(moveStringParts[3])
            )
        }

        return ComplexPokemon(
            id,
            primaryType,
            secondaryType,
            gender,
            categoryName,
            abilityArray,
            weightInGrams.toInt(),
            heightInCm.toInt(),
            stats,
            species,
            movesArray
        )
    }
}