package dtu.group21.models.api

import dtu.group21.models.pokemon.SimplePokemon
import java.util.jar.Attributes.Name

class PokedexRequestMaker {

    val apiURL = "https://pokeapi.co/api/v2"
    val jsonRequestMaker: JsonRequestMaker = JsonRequestMaker(apiURL)

    fun getSimplePokemon(pokedexId: Int): SimplePokemon {
        val species = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")
        val names = species.getJSONArray("names")
        var name = ""

        for (i in 0 until names.length()) {
            val nameObject = names.getJSONObject(i)
            val language = nameObject.getJSONObject("language").getString("name")

            if (!language.equals("en")) {
                continue;
            }
            name = nameObject.getString("name")
        }

        return SimplePokemon(pokedexId, name)

    }


}