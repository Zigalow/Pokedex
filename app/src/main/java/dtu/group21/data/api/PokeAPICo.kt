package dtu.group21.data.api

import androidx.compose.runtime.mutableStateOf
import dtu.group21.models.api.JsonRequestMaker
import dtu.group21.models.pokemon.DetailedPokemon
import dtu.group21.models.pokemon.DisplayPokemon
import dtu.group21.models.pokemon.MoveDamageClass
import dtu.group21.models.pokemon.PokemonAbility
import dtu.group21.models.pokemon.PokemonMove
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType
import org.json.JSONArray

class PokeAPICo : PokemonAPI {
    private val apiURL = "https://pokeapi.co/api/v2"
    private val jsonRequestMaker: JsonRequestMaker = JsonRequestMaker(apiURL)

    override suspend fun getDisplayPokemon(pokedexId: Int): DisplayPokemon {
        val pokemonResponse = jsonRequestMaker.makeRequest("pokemon/$pokedexId")

        val idName = pokemonResponse.getString("name")
        val types = pokemonResponse.getJSONArray("types")
        val primaryTypeName = types.getJSONObject(0).getJSONObject("type").getString("name")
        val secondaryTypeName =
            if (types.length() == 2) types.getJSONObject(1).getJSONObject("type")
                .getString("name") else "none"

        // TODO should probably have an actual class instead of this anonymous class
        val pokemon = object : DisplayPokemon {
            override val name: String = idName
            override val pokedexId: Int = pokedexId
            override val primaryType: PokemonType = PokemonType.getFromName(primaryTypeName)
            override val secondaryType: PokemonType = PokemonType.getFromName(secondaryTypeName)
            override val spriteId: String =
                "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${
                    pokedexId.toString().padStart(3, '0')
                }.png"
            override val hasTwoTypes: Boolean = secondaryTypeName != "none"
        }

        return pokemon
    }

    override suspend fun getDetailedPokemon(pokedexId: Int): DetailedPokemon {
        // TODO: maybe make this implement it itself, as there could be possibilities of optimizations
        return getDetailedPokemon(getDisplayPokemon(pokedexId))
    }

    private fun getIdNames(array: JSONArray, nameObject: String) =
        (0 until array.length()).map { i ->
            array.getJSONObject(i).getJSONObject(nameObject).getString("name")
        }

    private fun getLanguageString(
        stringObjects: JSONArray,
        stringName: String,
        targetLanguage: String = "en"
    ): String {
        for (i in 0 until stringObjects.length()) {
            val stringObject = stringObjects.getJSONObject(i)
            val languageName = stringObject.getJSONObject("language").getString("name")

            if (languageName.equals(targetLanguage)) {
                return stringObject.getString(stringName)
            }
        }
        return ""
    }

    private suspend fun getMove(moveName: String): PokemonMove {
        val moveObject = jsonRequestMaker.makeRequest("move/$moveName")

        val namesArray = moveObject.getJSONArray("names")
        val name = getLanguageString(namesArray, "name")
        val descriptionsArray = moveObject.getJSONArray("flavor_text_entries")
        val description = getLanguageString(descriptionsArray, "flavor_text")

        val power = moveObject.get("power") as? Int
        val accuracy = moveObject.get("accuracy") as? Int
        val pp = moveObject.getInt("pp")
        val type = PokemonType.getFromName(moveObject.getJSONObject("type").getString("name"))
        val damageClass =
            MoveDamageClass.getFromName(moveObject.getJSONObject("damage_class").getString("name"))

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

    private suspend fun getAbility(abilityName: String, isHidden: Boolean): PokemonAbility {
        val abilityObject = jsonRequestMaker.makeRequest("ability/$abilityName")

        val namesArray = abilityObject.getJSONArray("names")
        val name = getLanguageString(namesArray, "name")
        val descriptionsArray = abilityObject.getJSONArray("flavor_text_entries")
        val description = getLanguageString(descriptionsArray, "flavor_text")

        return PokemonAbility(
            name,
            description,
            isHidden,
        )
    }

    override suspend fun getDetailedPokemon(templatePokemon: DisplayPokemon): DetailedPokemon {
        val pokedexId = templatePokemon.pokedexId

        val pokemonResponse = jsonRequestMaker.makeRequest("pokemon/$pokedexId")
        val speciesResponse = jsonRequestMaker.makeRequest("pokemon-species/$pokedexId")

        val statsList = (0..5).map { i ->
            pokemonResponse.getJSONArray("stats").getJSONObject(i).getInt("base_stat")
        }
        val stats = PokemonStats(statsList)
        val heightInCm = pokemonResponse.getInt("height") * 10
        val weightInGrams = pokemonResponse.getInt("weight") * 100

        val moveIdNames = getIdNames(pokemonResponse.getJSONArray("moves"), "move")
        val moves = moveIdNames.map { getMove(it) }

        val abilityIdNames = getIdNames(pokemonResponse.getJSONArray("abilities"), "ability")
        val abilities = abilityIdNames.map { getAbility(it, false) } // TODO: actually figure out if it's hidden

        val evolutionChainId =
            speciesResponse.getJSONObject("evolution_chain").getString("url").split("/").dropLast(1)
                .last().toInt()
        val genderRate = speciesResponse.getInt("gender_rate")
        val isBaby = speciesResponse.getBoolean("is_baby")
        val isLegendary = speciesResponse.getBoolean("is_legendary")
        val isMythical = speciesResponse.getBoolean("is_mythical")
        val categoryName = getLanguageString(speciesResponse.getJSONArray("genera"), "genus")

        // TODO should probably have an actual class instead of this anonymous class
        val pokemon = object : DetailedPokemon {
            // TODO: in my opinion should not be in the class
            override var isFavorite = mutableStateOf(false)
            override val moves: Array<PokemonMove> = moves.toTypedArray()
            override val stats: PokemonStats = stats
            override val evolutionChainId: Int = evolutionChainId
            override val genderRate: Int = genderRate
            override val isBaby: Boolean = isBaby
            override val isLegendary: Boolean = isLegendary
            override val isMythical: Boolean = isMythical
            override val category: String = categoryName
            override val generation: Int = 0 // TODO
            override val weightInGrams: Int = weightInGrams
            override val heightInCm: Int = heightInCm
            override val abilities: Array<PokemonAbility> = abilities.toTypedArray()
            override val name: String = templatePokemon.name
            override val pokedexId: Int = templatePokemon.pokedexId
            override val primaryType: PokemonType = templatePokemon.primaryType
            override val secondaryType: PokemonType = templatePokemon.secondaryType
            override val spriteId: String = templatePokemon.spriteId
            override val hasTwoTypes: Boolean = templatePokemon.hasTwoTypes
        }

        return pokemon
    }

    override suspend fun getMove(moveId: Int): PokemonMove {
        TODO("Not yet implemented")
    }
}