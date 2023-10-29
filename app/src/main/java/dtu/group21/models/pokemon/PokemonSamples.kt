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

    val vineWhip = PokemonMove(
        "vine_whip",
        MoveEffectCategory.PHYSICAL,
        MoveCategory.LEVEL_UP,
        PokemonType.GRASS,
        45,
        100,
        25,
        "",
        13
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


object BulbasaurMoves {

    val tackle = PokemonMove(
        "Tackle",
        MoveEffectCategory.PHYSICAL,
        MoveCategory.LEVEL_UP,
        PokemonType.NORMAL,
        35,
        95,
        35,
        "A physical attack in which the user charges and slams into the target with its whole body.",
        1
    )

    val growl = PokemonMove(
        "Growl",
        MoveEffectCategory.STATUS,
        MoveCategory.LEVEL_UP,
        PokemonType.NORMAL,
        0,
        100,
        40,
        "The user growls in an endearing way, making the opposing team less wary. This lowers their Attack stats.",
        3
    )

    val leechSeed = PokemonMove(
        "Leech Seed",
        MoveEffectCategory.STATUS,
        MoveCategory.LEVEL_UP,
        PokemonType.GRASS,
        0,
        90,
        10,
        "A seed is planted on the target. It steals some HP from the target every turn.",
        7
    )

    val vineWhip = PokemonMove(
        "Vine Whip",
        MoveEffectCategory.PHYSICAL,
        MoveCategory.LEVEL_UP,
        PokemonType.GRASS,
        45,
        100,
        25,
        "The target is struck with slender, whiplike vines to inflict damage.",
        13
    )

    val poisonPowder = PokemonMove(
        "Poison Powder",
        MoveEffectCategory.STATUS,
        MoveCategory.LEVEL_UP,
        PokemonType.POISON,
        0,
        75,
        35,
        "The user scatters a powder that may poison the target.",
        20
    )

    val sleepPowder = PokemonMove(
        "Sleep Powder",
        MoveEffectCategory.STATUS,
        MoveCategory.LEVEL_UP,
        PokemonType.GRASS,
        0,
        75,
        15,
        "The user scatters a powder that may cause the target to fall asleep.",
        27
    )

    val takeDown = PokemonMove(
        "Take Down",
        MoveEffectCategory.PHYSICAL,
        MoveCategory.LEVEL_UP,
        PokemonType.NORMAL,
        90,
        85,
        20,
        "A reckless attack that also hurts the user. The user gains power if it's attacked.",
        34
    )

    val razorLeaf = PokemonMove(
        "Razor Leaf",
        MoveEffectCategory.PHYSICAL,
        MoveCategory.LEVEL_UP,
        PokemonType.GRASS,
        55,
        95,
        25,
        "Sharp-edged leaves are launched to slash at the opposing team. Critical hits land more easily.",
        41
    )

    val sweetScent = PokemonMove(
        "Sweet Scent",
        MoveEffectCategory.STATUS,
        MoveCategory.LEVEL_UP,
        PokemonType.NORMAL,
        0,
        100,
        20,
        "A sweet scent that harshly lowers the opposing team's evasiveness.",
        48
    )
}


var BulbasaurMovesList: List<PokemonMove> = listOf(
    BulbasaurMoves.tackle,        // Index 0: Tackle
    BulbasaurMoves.growl,         // Index 1: Growl
    BulbasaurMoves.leechSeed,     // Index 2: Leech Seed
    BulbasaurMoves.vineWhip,      // Index 3: Vine Whip
    BulbasaurMoves.poisonPowder,  // Index 4: Poison Powder
    BulbasaurMoves.sleepPowder,   // Index 5: Sleep Powder
    BulbasaurMoves.takeDown,      // Index 6: Take Down
    BulbasaurMoves.razorLeaf,     // Index 7: Razor Leaf
    BulbasaurMoves.sweetScent     // Index 8: Sweet Scent
)




