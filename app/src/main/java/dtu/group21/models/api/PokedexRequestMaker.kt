package dtu.group21.models.api

import dtu.group21.models.pokemon.EvolutionChainPokemon
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


    suspend fun getSimplePokemon(pokedexId: Int): EvolutionChainPokemon {
        val speciesObject = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(speciesObject)
        val evolutionChainId =
            speciesObject.getJSONObject("evolution_chain").getString("url").dropLast(1).split("/")
                .last().toInt()
        val evolutionInfo = getEvolutionInfo(evolutionChainId, pokedexId)

        return EvolutionChainPokemon(pokedexId, name, evolutionInfo.first, evolutionInfo.second)
    }
}