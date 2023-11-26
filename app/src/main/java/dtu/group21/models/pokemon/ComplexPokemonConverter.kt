package dtu.group21.models.pokemon

import androidx.room.TypeConverter

class ComplexPokemonConverter {
    @TypeConverter
    fun abilitiesToStoredString(abilities: Array<PokemonAbility>): String {
        val sb = StringBuilder()

        for (ability in abilities) {
            sb.append('{')
            sb.append(ability.name)
            sb.append(';')
            sb.append(ability.description)
            sb.append(';')
            sb.append(ability.isHidden)
            sb.append('}')
            sb.append(',')
        }
        sb.dropLast(1)

        return sb.toString()
    }

    @TypeConverter
    fun storedStringToAbilities(value: String): Array<PokemonAbility> {
        val abilityStrings = value.split(",")

        return Array(abilityStrings.size) {
            val abilityString = abilityStrings[it]
            val abilityProperties = abilityString.split(";")
            val name = abilityProperties[0]
            val description = abilityProperties[1]
            val isHidden = abilityProperties[2].toBoolean()
            PokemonAbility(name, description, isHidden)
        }
    }

    @TypeConverter
    fun pokemonStatsToStoredString(stats: PokemonStats) = "${stats.hp};${stats.attack};${stats.defense};${stats.specialAttack};${stats.specialDefense};${stats.speed}"

    @TypeConverter
    fun storedStringToPokemonStats(value: String): PokemonStats {
        val properties = value.split(";")
        return PokemonStats(
            properties[0].toInt(),
            properties[1].toInt(),
            properties[2].toInt(),
            properties[3].toInt(),
            properties[4].toInt(),
            properties[5].toInt(),
        )
    }
}