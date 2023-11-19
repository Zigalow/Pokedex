package dtu.group21.models.api

import com.example.pokedex.R
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.models.pokemon.PreviewPokemon
import dtu.group21.models.pokemon.EvolutionChainPokemon
import dtu.group21.models.pokemon.PokemonMove
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
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

        // Species
        val hasGenderDifferences = species.getBoolean("has_gender_difference")
        val isLegendary = species.getBoolean("is_legendary")
        val isBaby = species.getBoolean("is_baby")
        val isMythical = species.getBoolean("is_mythical")

        return ComplexPokemon(
            pokedexId,
            types.first,
            types.second,
            defaultGender,
            PokemonStats(0, 0, 0, 0, 0, 0),
            PokemonSpecies(
                name,
                hasGenderDifferences,
                isBaby,
                isLegendary,
                isMythical
            ),
            R.drawable._0001,
            emptyArray<PokemonMove>()
        )
    }
}