package dtu.group21.data.database

import androidx.room.TypeConverter
import dtu.group21.data.pokemon.PokemonAbility
import dtu.group21.data.pokemon.PokemonGender
import dtu.group21.data.pokemon.PokemonStats
import dtu.group21.data.pokemon.PokemonType
import dtu.group21.data.pokemon.moves.MoveDamageClass
import dtu.group21.data.pokemon.moves.PokemonMove

class PokemonConverters {
    @TypeConverter
    fun fromType(type: PokemonType?): String? = type?.name

    @TypeConverter
    fun toType(typeString: String?): PokemonType? =
        if (typeString != null) PokemonType.valueOf(typeString) else null

    @TypeConverter
    fun fromGender(gender: PokemonGender?): String? = gender?.name

    @TypeConverter
    fun toGender(genderString: String?): PokemonGender? =
        if (genderString != null) PokemonGender.valueOf(genderString) else null

    @TypeConverter
    fun fromStats(stats: PokemonStats?): String? {
        return stats?.toList()?.joinToString(";")
    }

    @TypeConverter
    fun toStats(statsString: String?): PokemonStats? {
        return if (statsString != null) PokemonStats(
            statsString.split(";").map { it.toInt() }) else null
    }

    @TypeConverter
    fun fromAbility(ability: PokemonAbility?): String? {
        return if (ability != null) "${ability.name};;${ability.description};;${ability.isHidden}" else null
    }

    @TypeConverter
    fun toAbility(abilityString: String?): PokemonAbility? {
        if (abilityString == null)
            return null

        val parts = abilityString.split(";;")
        val (name, description, isHiddenString) = parts
        val isHidden = (isHiddenString == "true")

        return PokemonAbility(name, description, isHidden)
    }

    @TypeConverter
    fun fromAbilities(abilities: Array<PokemonAbility>?) = ConverterHelper.fromArray(abilities, { fromAbility(it)!! }, ";;;")
    @TypeConverter
    fun toAbilities(abilitiesString: String?): Array<PokemonAbility>? = ConverterHelper.toList(abilitiesString, { toAbility(it)!! }, ";;;")?.toTypedArray()

    @TypeConverter
    fun fromMove(move: PokemonMove?): String? {
        if (move == null)
            return null

        return "'${move.name}';;'${move.description}';;${move.power};;${move.accuracy};;${move.pp};;${
            fromType(
                move.type
            )
        };;${move.damageClass}"
    }
    @TypeConverter
    fun toMove(moveString: String?): PokemonMove? {
        if (moveString == null)
            return null

        val parts = moveString.split(";;")

        val name = parts[0].drop(1).dropLast(1)
        val description = parts[1].drop(1).dropLast(1)
        val power = parts[2].toIntOrNull()
        val accuracy = parts[3].toIntOrNull()
        val pp = parts[4].toInt()
        val type = PokemonType.valueOf(parts[5])
        val damageClass = MoveDamageClass.valueOf(parts[6])

        return PokemonMove(
            name, description, power, accuracy, pp, type, damageClass
        )
    }

    @TypeConverter
    fun fromMoves(moves: Array<PokemonMove>?) = ConverterHelper.fromArray(moves, { fromMove(it)!! }, ";;;")
    @TypeConverter
    fun toMoves(movesString: String?): Array<PokemonMove>? = ConverterHelper.toList(movesString, { toMove(it)!! }, ";;;")?.toTypedArray()
}