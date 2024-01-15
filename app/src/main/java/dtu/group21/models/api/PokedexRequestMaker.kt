package dtu.group21.models.api

import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.models.pokemon.PreviewPokemon
import dtu.group21.models.pokemon.EvolutionChainPokemon
import dtu.group21.models.pokemon.MoveDamageClass
import dtu.group21.models.pokemon.PokemonAbility
import dtu.group21.models.pokemon.PokemonMove
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.moves.BasicMove
import dtu.group21.models.pokemon.moves.DisplayMove
import dtu.group21.models.pokemon.moves.EggMoveData
import dtu.group21.models.pokemon.moves.LevelMoveData
import dtu.group21.models.pokemon.moves.MachineMoveData
import dtu.group21.models.pokemon.moves.TutorMoveData
import org.json.JSONArray
import org.json.JSONObject

class PokedexRequestMaker {

    private val apiURL = "https://pokeapi.co/api/v2"
    private val jsonRequestMaker: JsonRequestMaker = JsonRequestMaker(apiURL)


    private fun getNameFromSpecies(species: JSONObject): String {
        val names = species.getJSONArray("names")
        var name = ""

        for (i in 0 until names.length()) {
            val nameObject = names.getJSONObject(i)
            val language = nameObject.getJSONObject("language").getString("name")

            if (!language.equals("en")) {
                continue
            }
            name = nameObject.getString("name")
        }
        return name
    }

    private fun getPokemonTypes(pokemon: JSONObject): Pair<PokemonType, PokemonType> {

        val types = pokemon.getJSONArray("types")
        val primaryType =
            PokemonType.getFromName(types.getJSONObject(0).getJSONObject("type").getString("name"))
        val secondaryType =
            if (types.length() == 2) {
                PokemonType.getFromName(
                    types.getJSONObject(1).getJSONObject("type").getString("name")
                )
            } else {
                PokemonType.NONE
            }

        return Pair(primaryType, secondaryType)
    }


    private fun getEvolutionInfo(
        evolutionInfo: JSONObject,
        targetId: Int,
        precedingId: Int = -1
    ): Triple<Boolean, Int, IntArray> {
        val pokemonUrl = evolutionInfo.getJSONObject("species").getString("url").dropLast(1)
        val pokemonId = pokemonUrl.split("/").last().toInt()

        val evolutionsArray = evolutionInfo.getJSONArray("evolves_to")
        val evolutionIds = IntArray(evolutionsArray.length())
        for (i in 0 until evolutionsArray.length()) {
            val url = evolutionsArray.getJSONObject(i).getJSONObject("species").getString("url")
                .dropLast(1)
            evolutionIds[i] = url.split("/").last().toInt()
        }

        if (pokemonId == targetId) {
            return Triple(true, precedingId, evolutionIds)
        }
        for (i in 0 until evolutionsArray.length()) {
            val evolution = evolutionsArray.getJSONObject(i)
            val evolutionInfo = getEvolutionInfo(evolution, targetId, pokemonId)
            if (evolutionInfo.first)
                return evolutionInfo
        }
        return Triple(false, -1, IntArray(0))
    }

    private suspend fun getEvolutionInfo(
        evolutionChainId: Int,
        pokedexId: Int
    ): Pair<Int, IntArray> {
        val evolutionChainObject =
            jsonRequestMaker.makeRequest("evolution-chain/$evolutionChainId").getJSONObject("chain")
        val info = getEvolutionInfo(evolutionChainObject, pokedexId)

        return Pair(info.second, info.third)
    }

    private fun getEnglishString(
        stringObjects: JSONArray,
        stringName: String,
        targetLanguage: String = "en"
    ): String {
        for (i in 0 until stringObjects.length()) {
            val languageObject = stringObjects.getJSONObject(i)
            val languageName = languageObject.getJSONObject("language").getString("name")

            if (languageName.equals(targetLanguage)) {
                return languageObject.getString(stringName)
            }
        }
        return ""
    }


    suspend fun getSimplePokemon(pokedexId: Int): EvolutionChainPokemon {
        val speciesObject = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(speciesObject)
        val evolutionChainId =
            speciesObject.getJSONObject("evolution_chain").getString("url").dropLast(1).split("/")
                .last().toInt()
        val evolutionInfo = getEvolutionInfo(evolutionChainId, pokedexId)

        return EvolutionChainPokemon(pokedexId, name, evolutionInfo.first, evolutionInfo.second)
    }

    suspend fun getPreviewPokemon(pokedexId: Int): PreviewPokemon {
        val species = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(species)

        val pokemon = jsonRequestMaker.makeRequest("pokemon/$pokedexId")
        val types = getPokemonTypes(pokemon)

        return PreviewPokemon(pokedexId, name, types.first, types.second)
    }

    private suspend fun getMove(moveName: String): DisplayMove {
        val moveObject = jsonRequestMaker.makeRequest("move/$moveName")

        val namesArray = moveObject.getJSONArray("names")
        val name = getEnglishString(namesArray, "name")

        val power = moveObject.get("power") as? Int
        val accuracy = moveObject.get("accuracy") as? Int
        val pp = moveObject.getInt("pp")
        val type = PokemonType.getFromName(moveObject.getJSONObject("type").getString("name"))
        val damageClass =
            MoveDamageClass.getFromName(moveObject.getJSONObject("damage_class").getString("name"))

        return BasicMove(
            name,
            power,
            accuracy,
            pp,
            type,
            damageClass
        )
    }
    private suspend fun getBasicMove(moveName: String, pokedexId: Int): DisplayMove {
        val moveObject = jsonRequestMaker.makeRequest("move/$moveName")

        // Pokemon's ID
        val pokemonResponse = jsonRequestMaker.makeRequest("pokemon/$pokedexId")

        // move's name
        val namesArray = moveObject.getJSONArray("names")
        val name = getEnglishString(namesArray, "name")

        // Other data
        val power = moveObject.get("power") as? Int
        val accuracy = moveObject.get("accuracy") as? Int
        val pp = moveObject.getInt("pp")

        val type = PokemonType.getFromName(moveObject.getJSONObject("type").getString("name"))

        // Move categories name
        val moves = pokemonResponse.getJSONArray("moves")
        val learnMoveMethods = moves.getJSONObject(0).getJSONArray("version_group_details")
        val moveMethodName = learnMoveMethods.getJSONObject(0).getJSONObject("move_learn_method").getString("name")
        val damageClass =
            MoveDamageClass.getFromName(moveObject.getJSONObject("damage_class").getString("name"))


        return when (moveMethodName) {
            "machine" -> {
                val machines = moveObject.getJSONArray("machines")

                val machineURL = IdFromUrl(machines.getJSONObject(0).getJSONObject("machine").getString("url"))

                val machineObject = jsonRequestMaker.makeRequest("move/$machineURL")
                val machineID = machineObject.getString("id")

                MachineMoveData(name, power, accuracy, pp, type, damageClass, machineID)
            }
            "level-up" -> {
                val move = pokemonResponse.getJSONObject("moves").getJSONObject("move")
                val learnMoveName = move.getString("name")

                var level = 0
                if (learnMoveName == name) {
                    for(i in 0 until learnMoveMethods.length()) {
                        level = learnMoveMethods.getJSONObject(i).getInt("level_learned_at")
                    }
                }
                LevelMoveData(name, power, accuracy, pp, type, damageClass, level)

            }
            "tutor" -> {
                TutorMoveData(name, power, accuracy, pp, type, damageClass)
            }
            "egg" -> {
                EggMoveData(name, power, accuracy, pp, type, damageClass)
            }
            else -> {
                BasicMove(name, power, accuracy, pp, type, damageClass)
            }
        }
    }

    private fun IdFromUrl( url: String): Int {
        return url.split("/").dropLast(1).last().toInt()
    }
    private suspend fun getAbility(abilityName: String, isHidden: Boolean): PokemonAbility {
        val abilityObject = jsonRequestMaker.makeRequest("ability/$abilityName")

        val namesArray = abilityObject.getJSONArray("names")
        val name = getEnglishString(namesArray, "name")
        val descriptionsArray = abilityObject.getJSONArray("flavor_text_entries")
        val description = getEnglishString(descriptionsArray, "flavor_text")


        return PokemonAbility(
            name,
            description,
            isHidden,
        )
    }

    suspend fun getComplexPokemon(pokedexId: Int): ComplexPokemon {
        val species = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(species)

        val pokemon = jsonRequestMaker.makeRequest("pokemon/$pokedexId")
        val types = getPokemonTypes(pokemon)

        val genderRate = species.getInt("gender_rate")
        val defaultGender = when (genderRate) {
            -1 -> PokemonGender.GENDERLESS
            8 -> PokemonGender.FEMALE
            else -> PokemonGender.MALE
        }
        val categoryName = getEnglishString(species.getJSONArray("genera"), "genus")
        val weightInGrams = pokemon.getInt("weight") * 100
        val heightInCm = pokemon.getInt("height") * 10

        // Stats
        val statsArray = pokemon.getJSONArray("stats")
        val statValues = IntArray(statsArray.length())
        for (i in 0 until statsArray.length()) {
            statValues[i] = statsArray.getJSONObject(i).getInt("base_stat")
        }
        val pokemonStats = PokemonStats(
            statValues[0],
            statValues[1],
            statValues[2],
            statValues[3],
            statValues[4],
            statValues[5]
        )

        // Species
        val hasGenderDifferences = species.getBoolean("has_gender_differences")
        val isLegendary = species.getBoolean("is_legendary")
        val isBaby = species.getBoolean("is_baby")
        val isMythical = species.getBoolean("is_mythical")

        // Moves
        val movesArray = pokemon.getJSONArray("moves")
        val pokemonMoves: Array<DisplayMove> = Array(movesArray.length()) { _ ->
            BasicMove(
                "",
                0,
                0,
                0,
                PokemonType.NONE,
                MoveDamageClass.PHYSICAL
            )
        }
        for (i in 0 until movesArray.length()) {
            pokemonMoves[i] = getBasicMove(movesArray.getJSONObject(i).getJSONObject("move").getString("name"),pokedexId)

            //getMove(movesArray.getJSONObject(i).getJSONObject("move").getString("name"))
        }

        // Abilities
        val abilitiesArray = pokemon.getJSONArray("abilities")
        val pokemonAbilities = Array(abilitiesArray.length()) { _ ->
            PokemonAbility(
                "",
                "",
                false
            )
        }
        var isHidden: Boolean
        for (i in 0 until abilitiesArray.length()) {
            isHidden = abilitiesArray.getJSONObject(i).getBoolean("is_hidden")
            pokemonAbilities[i] =
                getAbility(
                    abilitiesArray.getJSONObject(i).getJSONObject("ability").getString("name"),
                    isHidden
                )
        }

        return ComplexPokemon(
            pokedexId,
            types.first,
            types.second,
            defaultGender,
            categoryName,
            pokemonAbilities,
            weightInGrams,
            heightInCm,
            pokemonStats,
            PokemonSpecies(
                name,
                genderRate,
                hasGenderDifferences,
                isBaby,
                isLegendary,
                isMythical
            ),
            pokemonMoves
        )
    }
}