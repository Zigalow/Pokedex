package dtu.group21.data.api

import androidx.compose.runtime.mutableStateOf
import dtu.group21.data.pokemon.AdvancedPokemon
import dtu.group21.data.pokemon.BasicPokemon
import dtu.group21.helpers.PokemonHelper
import dtu.group21.models.api.JsonRequestMaker
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.models.pokemon.MoveDamageClass
import dtu.group21.models.pokemon.MoveLearnMethod
import dtu.group21.models.pokemon.PokemonAbility
import dtu.group21.models.pokemon.PokemonMove
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.models.pokemon.moves.BasicMove
import dtu.group21.models.pokemon.moves.DisplayMove
import dtu.group21.models.pokemon.moves.LevelMoveData
import dtu.group21.models.pokemon.moves.MachineMoveData
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

        return BasicPokemon(
            name = PokemonHelper.getEnglishName(pokedexId, idName),
            pokedexId = pokedexId,
            primaryType = PokemonType.getFromName(primaryTypeName),
            secondaryType = PokemonType.getFromName(secondaryTypeName),
            spriteId = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${PokemonHelper.getPokedexIdString(pokedexId)}.png"
        )
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
    private suspend fun getBasicMove(moveName: String, templatePokemon: DisplayPokemon): DisplayMove {
        val moveObject = jsonRequestMaker.makeRequest("move/$moveName")

        val namesArray = moveObject.getJSONArray("names")
        val name = getLanguageString(namesArray, "name")

        val power = moveObject.get("power") as? Int
        val accuracy = moveObject.get("accuracy") as? Int
        val type = PokemonType.getFromName(moveObject.getJSONObject("type").getString("name"))

        // IF machine
        if(true) {
            val machines = moveObject.getJSONArray("machines")

            val machineURL =
                machines.getJSONObject(0).getJSONObject("machine").getString("url").split("/")
                    .dropLast(1)
                    .last().toInt()
            val machineObject = jsonRequestMaker.makeRequest("move/$machineURL")

            val machineID = machineObject.getString("id")

            return MachineMoveData(name, power, accuracy, type, machineID)
        } else if (false) {
            val pokedexId = templatePokemon.pokedexId
            val pokemonResponse = jsonRequestMaker.makeRequest("pokemon/$pokedexId")

            val move = pokemonResponse.getJSONObject("moves").getJSONObject("move")

            val learnMoveName = move.get("name")

            var level = 0
            if (learnMoveName == name) {
                val learnMoveMethods = moveObject.getJSONArray("version_group_details")
                for(i in 0 until learnMoveMethods.length()) {
                    level = learnMoveMethods.getJSONObject(i).getInt("level_learned_at")
                }
            }
            return LevelMoveData(name, power, accuracy, type, level)

        }else {
            return BasicMove(name, power, accuracy, type)
        }
    }

    /*private suspend fun getAdvancedMove(moveName: String): DetailedMove {
        val moveObject = jsonRequestMaker.makeRequest("move/$moveName")

        val namesArray = moveObject.getJSONArray("names")
        val name = getLanguageString(namesArray, "name")
        val descriptionsArray = moveObject.getJSONArray("flavor_text_entries")
        val description = getLanguageString(descriptionsArray, "flavor_text")

        val power = moveObject.get("power") as? Int
        val accuracy = moveObject.get("accuracy") as? Int
        val pp = moveObject.getInt("pp")
        val priority = moveObject.getInt("priority")

        val type = PokemonType.getFromName(moveObject.getJSONObject("type").getString("name"))
        val damageClass = MoveDamageClass.getFromName(moveObject.getJSONObject("damage_class").getString("name"))


        return AdvancedMove(damageClass,  pp, description, BasisMove)
        }
    }*/

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

        return AdvancedPokemon(
            template = templatePokemon,
            moves = moves.toTypedArray(),
            stats = stats,
            evolutionChainId = evolutionChainId,
            genderRate = genderRate,
            isBaby = isBaby,
            isLegendary = isLegendary,
            isMythical = isMythical,
            category = categoryName,
            generation = PokemonHelper.getGeneration(pokedexId),
            weightInGrams = weightInGrams,
            heightInCm = heightInCm,
            abilities = abilities.toTypedArray(),
            // TODO: in my opinion should not be in the class
            isFavorite = mutableStateOf(false),
        )
    }

    override suspend fun getMove(moveId: Int): BasicMove {
        TODO("Not yet implemented")
    }
}