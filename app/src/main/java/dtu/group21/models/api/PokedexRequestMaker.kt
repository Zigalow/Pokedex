package dtu.group21.models.api

import dtu.group21.models.pokemon.PokemonType
import dtu.group21.models.pokemon.PreviewPokemon
import dtu.group21.models.pokemon.SimplePokemon
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

    fun getSimplePokemon(pokedexId: Int): SimplePokemon {
        val species = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(species)

        return SimplePokemon(pokedexId, name)
    }

    fun getPreviewPokemon(pokedexId: Int): PreviewPokemon {
        val species = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val name = getNameFromSpecies(species)

        val pokemon = jsonRequestMaker.makeRequest("pokemon/$pokedexId")
        val types = getPokemonTypes(pokemon)

        return PreviewPokemon(pokedexId, name, types.first, types.second)
    }


}