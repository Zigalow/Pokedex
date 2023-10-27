package dtu.group21.models.pokemon

import com.example.pokedex.R

object PokemonSamples {

    val bulbasaur = Pokemon(
        "bulbasaur",
        1,
        PokemonType.GRASS,
        PokemonType.POISON,
        PokemonGender.MALE,
        PokemonStats(45, 49, 49, 65, 65, 45),
        R.drawable._0001
    )

    val ivysaur = Pokemon(
        "ivysaur",
        2,
        PokemonType.GRASS,
        PokemonType.POISON,
        PokemonGender.MALE,
        PokemonStats(60, 62, 63, 80, 80, 60),
        R.drawable._0002
    )

    val venusaur = Pokemon(
        "venusaur",
        3,
        PokemonType.GRASS,
        PokemonType.POISON,
        PokemonGender.MALE,
        PokemonStats(80, 82, 83, 100, 100, 80),
        R.drawable._0003
    )

    val charmander = Pokemon(
        "charmander",
        4,
        PokemonType.FIRE,
        PokemonType.NONE,
        PokemonGender.MALE,
        PokemonStats(39, 52, 43, 60, 50, 65),
        R.drawable._0004
    )

    val charmeleon = Pokemon(
        "charmeleon",
        5,
        PokemonType.FIRE,
        PokemonType.NONE,
        PokemonGender.MALE,
        PokemonStats(58, 64, 58, 80, 65, 80),
        R.drawable._0005
    )

    val charizard = Pokemon(
        "charizard",
        6,
        PokemonType.FIRE,
        PokemonType.FLYING,
        PokemonGender.MALE,
        PokemonStats(78, 84, 78, 109, 85, 100),
        R.drawable._0006
    )

    val squirtle = Pokemon(
        "squirtle",
        7,
        PokemonType.WATER,
        PokemonType.NONE,
        PokemonGender.MALE,
        PokemonStats(44, 48, 65, 50, 64, 43),
        R.drawable._0007
    )

    val wartortle = Pokemon(
        "wartortle",
        8,
        PokemonType.WATER,
        PokemonType.NONE,
        PokemonGender.MALE,
        PokemonStats(59, 63, 80, 65, 80, 58),
        R.drawable._0008
    )

    val blastoise = Pokemon(
        "blastoise",
        9,
        PokemonType.WATER,
        PokemonType.NONE,
        PokemonGender.MALE,
        PokemonStats(79, 83, 100, 85, 105, 78),
        R.drawable._0009
    )

    val caterpie = Pokemon(
        "caterpie",
        10,
        PokemonType.BUG,
        PokemonType.NONE,
        PokemonGender.MALE,
        PokemonStats(45, 30, 35, 20, 20, 45),
        R.drawable._0010
    )

    val metapod = Pokemon(
        "metapod",
        11,
        PokemonType.BUG,
        PokemonType.NONE,
        PokemonGender.MALE,
        PokemonStats(50, 20, 55, 25, 25, 30),
        R.drawable._0011
    )

    val butterfree = Pokemon(
        "butterfree",
        12,
        PokemonType.BUG,
        PokemonType.FLYING,
        PokemonGender.MALE,
        PokemonStats(60, 45, 50, 90, 80, 70),
        R.drawable._0012
    )

    var listOfPokemons: List<Pokemon> = listOf(
        bulbasaur, ivysaur, venusaur, charmander,
        charmeleon, charizard, squirtle, wartortle,
        blastoise, caterpie, metapod, butterfree
    )

}