package dtu.group21.models.pokemon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.pokedex.R
import dtu.group21.models.api.PokedexRequestMaker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

val requester = PokedexRequestMaker()

@OptIn(DelicateCoroutinesApi::class)
object PokemonSamples {
    /*  val bulbasaur = ComplexPokemon(
          1,
          PokemonType.GRASS,
          PokemonType.POISON,
          PokemonGender.MALE,
          PokemonStats(45, 49, 49, 65, 65, 45),
          PokemonSpecies(
              "Bulbasaur",
              false,
              false,
              false,
              false,
              PokemonEvolutionChain(emptyArray())
          ),
          R.drawable._0001
      )
  
      val ivysaur = ComplexPokemon(
          2,
          PokemonType.GRASS,
          PokemonType.POISON,
          PokemonGender.MALE,
          PokemonStats(60, 62, 63, 80, 80, 60),
          PokemonSpecies("Ivysaur", false, false, false, false, PokemonEvolutionChain(emptyArray())),
          R.drawable._0002
      )
  
      val venusaur = ComplexPokemon(
          3,
          PokemonType.GRASS,
          PokemonType.POISON,
          PokemonGender.MALE,
          PokemonStats(80, 82, 83, 100, 100, 80),
          PokemonSpecies("Venusaur", false, false, false, false, PokemonEvolutionChain(emptyArray())),
          R.drawable._0003
      )
  
      val charmander = ComplexPokemon(
          4,
          PokemonType.FIRE,
          PokemonType.NONE,
          PokemonGender.MALE,
          PokemonStats(39, 52, 43, 60, 50, 65),
          PokemonSpecies(
              "Charmander",
              false,
              false,
              false,
              false,
              PokemonEvolutionChain(emptyArray())
          ),
          R.drawable._0004
      )
  
      val charmeleon = ComplexPokemon(
          5,
          PokemonType.FIRE,
          PokemonType.NONE,
          PokemonGender.MALE,
          PokemonStats(58, 64, 58, 80, 65, 80),
          PokemonSpecies(
              "Charmeleon",
              false,
              false,
              false,
              false,
              PokemonEvolutionChain(emptyArray())
          ),
          R.drawable._0005
      )
  
      val charizard = ComplexPokemon(
          6,
          PokemonType.FIRE,
          PokemonType.FLYING,
          PokemonGender.MALE,
          PokemonStats(78, 84, 78, 109, 85, 100),
          PokemonSpecies(
              "Charizard",
              false,
              false,
              false,
              false,
              PokemonEvolutionChain(emptyArray())
          ),
          R.drawable._0006
      )
  
      val squirtle = ComplexPokemon(
          7,
          PokemonType.WATER,
          PokemonType.NONE,
          PokemonGender.MALE,
          PokemonStats(44, 48, 65, 50, 64, 43),
          PokemonSpecies("Squirtle", false, false, false, false, PokemonEvolutionChain(emptyArray())),
          R.drawable._0007
      )
  
      val wartortle = ComplexPokemon(
          8,
          PokemonType.WATER,
          PokemonType.NONE,
          PokemonGender.MALE,
          PokemonStats(59, 63, 80, 65, 80, 58),
          PokemonSpecies(
              "Wartortle",
              false,
              false,
              false,
              false,
              PokemonEvolutionChain(emptyArray())
          ),
          R.drawable._0008
      )
  
      val blastoise = ComplexPokemon(
          9,
          PokemonType.WATER,
          PokemonType.NONE,
          PokemonGender.MALE,
          PokemonStats(79, 83, 100, 85, 105, 78),
          PokemonSpecies(
              "Blastoise",
              false,
              false,
              false,
              false,
              PokemonEvolutionChain(emptyArray())
          ),
          R.drawable._0009
      )
  
      val caterpie = ComplexPokemon(
          10,
          PokemonType.BUG,
          PokemonType.NONE,
          PokemonGender.MALE,
          PokemonStats(45, 30, 35, 20, 20, 45),
          PokemonSpecies("Caterpie", false, false, false, false, PokemonEvolutionChain(emptyArray())),
          R.drawable._0010
      )
  
      val metapod = ComplexPokemon(
          11,
          PokemonType.BUG,
          PokemonType.NONE,
          PokemonGender.MALE,
          PokemonStats(50, 20, 55, 25, 25, 30),
          PokemonSpecies("Metapod", false, false, false, false, PokemonEvolutionChain(emptyArray())),
          R.drawable._0011
      )
  
      val butterfree = ComplexPokemon(
          12,
          PokemonType.BUG,
          PokemonType.FLYING,
          PokemonGender.MALE,
          PokemonStats(60, 45, 50, 90, 80, 70),
          PokemonSpecies(
              "Butterfree",
              false,
              false,
              false,
              false,
              PokemonEvolutionChain(emptyArray())
          ),
          R.drawable._0012
      )*/


    // Original
    /*val ids = 1..12
    val listOfPokemons: List<ComplexPokemon> by lazy {
        ids.map { requester.getComplexPokemon(it) }
    }*/


    // New
    private val ids = 1..12
    var listOfPokemons: List<ComplexPokemon> by mutableStateOf(emptyList())

    @Composable
    fun getPokemons(): List<ComplexPokemon> {
        // Use remember to fetch data only once
        var listOfPokemons by remember {
            mutableStateOf<List<ComplexPokemon>>(emptyList())
        }

        // Fetch data if not already fetched
        if (listOfPokemons.isEmpty()) {
            fetchPokemonData()
        }

        return listOfPokemons
    }

    fun fetchPokemonData() {
        // Fetch data asynchronously
        // Use Dispatchers.IO for background thread
        GlobalScope.launch(Dispatchers.IO) {
            val fetchedPokemons = ids.map { requester.getComplexPokemon(it) }

            // Update the state on the main thread
            withContext(Dispatchers.Main) {
                // Use mutableStateOf to update the state
                listOfPokemons = fetchedPokemons
            }
        }
    }

    

}


object BulbasaurMoves {

    val tackle = PokemonMove(
        "Tackle",
        "A physical attack in which the user charges and slams into the target with its whole body.",
        35,
        95,
        35,
        PokemonType.NORMAL,
        MoveDamageClass.PHYSICAL,
        /*  MoveLearnMethod.LEVEL_UP,
          1*/
    )

    val growl = PokemonMove(
        "Growl",
        "The user growls in an endearing way, making the opposing team less wary. This lowers their Attack stats.",
        0,
        100,
        40,
        PokemonType.NORMAL,
        MoveDamageClass.STATUS,
        /*MoveLearnMethod.LEVEL_UP,
        3*/
    )

    val leechSeed = PokemonMove(
        "Leech Seed",
        "A seed is planted on the target. It steals some HP from the target every turn.",
        0,
        90,
        10,
        PokemonType.GRASS,
        MoveDamageClass.STATUS,
        /*   MoveLearnMethod.LEVEL_UP,
           7*/
    )

    val vineWhip = PokemonMove(
        "Vine Whip",
        "The target is struck with slender, whiplike vines to inflict damage.",
        45,
        100,
        25,
        PokemonType.GRASS,
        MoveDamageClass.PHYSICAL,
        /* MoveLearnMethod.LEVEL_UP,
         13*/
    )

    val poisonPowder = PokemonMove(
        "Poison Powder",
        "The user scatters a powder that may poison the target.",
        0,
        75,
        35,
        PokemonType.POISON,
        MoveDamageClass.STATUS,
        /*MoveLearnMethod.LEVEL_UP,
        20*/
    )

    /*val sleepPowder = PokemonMove(
        "Sleep Powder",
        "The user scatters a powder that may cause the target to fall asleep.",
        0,
        75,
        15,
        PokemonType.GRASS,
        MoveDamageClass.STATUS,
        MoveLearnMethod.LEVEL_UP,
        27
    )

    val takeDown = PokemonMove(
        "Take Down",
        "A reckless attack that also hurts the user. The user gains power if it's attacked.",
        90,
        85,
        20,
        PokemonType.NORMAL,
        MoveDamageClass.PHYSICAL,
        MoveLearnMethod.LEVEL_UP,
        34
    )

    val razorLeaf = PokemonMove(
        "Razor Leaf",
        "Sharp-edged leaves are launched to slash at the opposing team. Critical hits land more easily.",
        55,
        95,
        25,
        PokemonType.GRASS,
        MoveDamageClass.PHYSICAL,
        MoveLearnMethod.LEVEL_UP,
        41
    )

    val sweetScent = PokemonMove(
        "Sweet Scent",
        "A sweet scent that harshly lowers the opposing team's evasiveness.",
        0,
        100,
        20,
        PokemonType.NORMAL,
        MoveDamageClass.STATUS,
        MoveLearnMethod.LEVEL_UP,
        48
    )*/
}


var BulbasaurMovesList: List<PokemonMove> = listOf(
    BulbasaurMoves.tackle,        // Index 0: Tackle
    BulbasaurMoves.growl,         // Index 1: Growl
    BulbasaurMoves.leechSeed,     // Index 2: Leech Seed
    BulbasaurMoves.vineWhip,      // Index 3: Vine Whip
    BulbasaurMoves.poisonPowder,  // Index 4: Poison Powder
    /* BulbasaurMoves.sleepPowder,   // Index 5: Sleep Powder
     BulbasaurMoves.takeDown,      // Index 6: Take Down
     BulbasaurMoves.razorLeaf,     // Index 7: Razor Leaf
     BulbasaurMoves.sweetScent     // Index 8: Sweet Scent*/
)




