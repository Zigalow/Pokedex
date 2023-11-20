package dtu.group21.models.api

import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.models.pokemon.PreviewPokemon
import dtu.group21.models.pokemon.EvolutionChainPokemon
import dtu.group21.models.pokemon.MoveDamageClass
import dtu.group21.models.pokemon.PokemonMove
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
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

    private fun getEvolutionInfo(evolutionChainId: Int, pokedexId: Int): Pair<Int, IntArray> {
        val evolutionChainObject =
            jsonRequestMaker.makeRequest("evolution-chain/$evolutionChainId").getJSONObject("chain")
        val info = getEvolutionInfo(evolutionChainObject, pokedexId)

        return Pair(info.second, info.third)
    }

    private fun getEnglishString(stringObjects: JSONArray, stringName: String, targetLanguage: String = "en"): String {
        for (i in 0 until stringObjects.length()) {
            val languageObject = stringObjects.getJSONObject(i)
            val languageName = languageObject.getJSONObject("language").getString("name")

            if (languageName.equals(targetLanguage)) {
                return languageObject.getString(stringName)
            }
        }
        return ""
    }


    fun getSimplePokemon(pokedexId: Int): EvolutionChainPokemon {
        val speciesObject = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(speciesObject)
        val evolutionChainId =
            speciesObject.getJSONObject("evolution_chain").getString("url").dropLast(1).split("/")
                .last().toInt()
        val evolutionInfo = getEvolutionInfo(evolutionChainId, pokedexId)

        return EvolutionChainPokemon(pokedexId, name, evolutionInfo.first, evolutionInfo.second)
    }

    fun getPreviewPokemon(pokedexId: Int): PreviewPokemon {
        val species = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(species)

        val pokemon = jsonRequestMaker.makeRequest("pokemon/$pokedexId")
        val types = getPokemonTypes(pokemon)

        return PreviewPokemon(pokedexId, name, types.first, types.second)
    }

    private fun getMove(moveName: String): PokemonMove {
        val moveObject = jsonRequestMaker.makeRequest("move/$moveName")

        val namesArray = moveObject.getJSONArray("names")
        val name = getEnglishString(namesArray, "name")
        val descriptionsArray = moveObject.getJSONArray("flavor_text_entries")
        val description = getEnglishString(descriptionsArray, "flavor_text")

        val power = moveObject.get("power") as? Int
        val accuracy = moveObject.get("accuracy") as? Int
        val pp = moveObject.getInt("pp")
        val type = PokemonType.getFromName(moveObject.getJSONObject("type").getString("name"))
        val damageClass = MoveDamageClass.getFromName(moveObject.getJSONObject("damage_class").getString("name"))

        return PokemonMove(
            name,
            description,
            power,
            accuracy,
            pp,
            type,
            damageClass,
        )
    }

    fun getComplexPokemon(pokedexId: Int): ComplexPokemon {
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

        // Stats
        val statsArray = pokemon.getJSONArray("stats")
        val statValues = IntArray(statsArray.length())
        for (i in 0 until statsArray.length()) {
            statValues[i] = statsArray.getJSONObject(i).getInt("base_stat")
        }
        val pokemonStats = PokemonStats(statValues[0], statValues[1], statValues[2], statValues[3], statValues[4], statValues[5])

        // Species
        val hasGenderDifferences = species.getBoolean("has_gender_differences")
        val isLegendary = species.getBoolean("is_legendary")
        val isBaby = species.getBoolean("is_baby")
        val isMythical = species.getBoolean("is_mythical")

        // Moves
        val movesArray = pokemon.getJSONArray("moves")
        val pokemonMoves = Array(movesArray.length()) { _ -> PokemonMove("", "", 0, 0, 0, PokemonType.NONE, MoveDamageClass.PHYSICAL) }
        for (i in 0 until movesArray.length()) {
            pokemonMoves[i] = getMove(movesArray.getJSONObject(i).getJSONObject("move").getString("name"))
        }

        return ComplexPokemon(
            pokedexId,
            types.first,
            types.second,
            defaultGender,
            pokemonStats,
            PokemonSpecies(
                name,
                hasGenderDifferences,
                isBaby,
                isLegendary,
                isMythical
            ),
            pokemonMoves
        )
    }
}