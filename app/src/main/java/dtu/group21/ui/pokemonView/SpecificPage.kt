package dtu.group21.ui.pokemonView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dtu.group21.data.PokedexViewModel
import dtu.group21.data.database.DatabaseViewModel
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.models.api.PokemonViewModel
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.EvolutionChainPokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.models.pokemon.moves.DisplayMove
import dtu.group21.models.pokemon.moves.EggMove
import dtu.group21.models.pokemon.moves.LevelMove
import dtu.group21.models.pokemon.moves.MachineMove
import dtu.group21.models.pokemon.moves.TutorMove
import dtu.group21.pokedex.MainActivity
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.frontpage.capitalizeFirstLetter
import dtu.group21.ui.frontpage.formatPokemonId
import dtu.group21.ui.theme.LightWhite

@Composable
fun SpecificPage(pokedexId: Int, onNavigateBack: () -> Unit) {
    val pokemon = remember {
        mutableStateOf(
            ComplexPokemon(
                0,
                PokemonType.NONE,
                PokemonType.NONE,
                PokemonGender.MALE,
                "",
                emptyArray(),
                0,
                0,
                PokemonStats(0, 0, 0, 0, 0, 0),
                PokemonSpecies("Loading", 0, false, false, false, false),
                emptyArray()
            ) as DetailedPokemon
        )
    }

    LaunchedEffect(Unit) {
        val viewModel = PokedexViewModel()
        viewModel.getDetails(pokedexId, pokemon)
    }
    Mid(modifier = Modifier, pokemon.value)
    Inspect(pokemon = pokemon.value, onNavigateBack = onNavigateBack)
}

@Composable
fun Inspect(pokemon: DetailedPokemon, onNavigateBack: () -> Unit) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(pokemon.primaryType.secondaryColor)
    }
    val modifier = Modifier
    Column(
        modifier
            .background(color = pokemon.primaryType.secondaryColor)
            .fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            Top(pokemon = pokemon, onClickBack = onNavigateBack)

            Mid(modifier, pokemon)
        }
        Column(
            modifier
                .fillMaxSize()
                .background(color = pokemon.primaryType.secondaryColor)
        ) {
            PokemonImage(
                pokemon = pokemon, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxHeight(0.4f)
            )
            Bottom(pokemon = pokemon)
        }
    }

}

@Composable
fun Top(
    pokemon: DetailedPokemon,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            backIcon(
                modifier
                    .padding(vertical = 16.dp, horizontal = 19.dp)
                    .size(49.dp)
                    .clickable { onClickBack() }
            )
        }
        //Spacer(modifier.width(230.dp))
        Box(
            modifier = Modifier
                .weight(0.1f)
        ) {
            FavoritesIcon(
                active = pokemon.isFavorite.value,
                color = pokemon.primaryType.secondaryColor,
                onClicked = {
                    pokemon.isFavorite.value = !pokemon.isFavorite.value
                    val database = MainActivity.database!!
                    val databaseViewModel = DatabaseViewModel()
                    val saveablePokemon = (pokemon as ComplexPokemon) // TODO
                    if (pokemon.isFavorite.value) {
                        println("Saving pokemon")
                        databaseViewModel.insertPokemon(saveablePokemon, database)
                    } else {
                        println("Deleting pokemon")
                        databaseViewModel.deletePokemon(saveablePokemon, database)
                    }
                }

            )
        }
        Spacer(modifier.width(11.dp))
    }
}

@Composable
fun Mid(modifier: Modifier = Modifier, pokemon: DetailedPokemon) {
    Column(
        modifier
            .height(105.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier
                .height(35.dp)
        ) {
            Spacer(modifier.width(13.dp))
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 30.sp
            )
            Text(
                text = formatPokemonId(pokemon.pokedexId),
                fontSize = 30.sp,
                color = pokemon.primaryType.primaryColor
            )
            Spacer(modifier.width(13.dp))
        }
        Spacer(modifier.height(24.dp))
        Row(
            modifier
                .height(35.dp)
        ) {
            Spacer(modifier.width(13.dp))
            LargerPokemonTypeBox(
                modifier
                    .width(80.dp)
                    .height(29.dp)
                    .background(
                        color = pokemon.primaryType.primaryColor,
                        shape = RoundedCornerShape(15.dp)
                    ), pokemonType = pokemon.primaryType
            )
            Spacer(modifier.width(15.dp))
            LargerPokemonTypeBox(
                modifier
                    .width(80.dp)
                    .height(29.dp)
                    .background(
                        color = pokemon.secondaryType.secondaryColor,
                        shape = RoundedCornerShape(15.dp)
                    ), pokemonType = pokemon.secondaryType
            )
        }
    }
}

@Composable
fun Bottom(modifier: Modifier = Modifier, pokemon: DetailedPokemon) {
    Column(
        modifier
            .fillMaxSize()
            .background(
                Color.White,
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ),
        verticalArrangement = Arrangement.Bottom
    ) {
        val categories = listOf("About", "Stats", "Moves", "Evolution")
        var selectedCategory by remember { mutableStateOf("About") }
        Spacer(
            modifier
                .width(13.dp)
                .height(25.dp)
        )
        Box(
            modifier = Modifier
                //.padding(horizontal = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            CategoryList(
                categories = categories,
                onCategorySelected = { selectedCategory = it },
                initiallyChosen = selectedCategory,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier.height(13.dp))
        Column(
            modifier
                .padding(start = 13.dp)
        ) {
            //based on which category is the coresponding section function will be used
            Sections(selectedCategory = selectedCategory, pokemon = pokemon, modifier = modifier)
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Composable
fun Category(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.Black else Color.Gray,
            textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
        )
    }
}

@Composable
fun CategoryList(
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    initiallyChosen: String = ""
) {
    var selectedCategory by remember { mutableStateOf(initiallyChosen) }

    LazyRow(
        modifier = modifier
            .height(25.dp)
            .background(shape = RoundedCornerShape(10.dp), color = LightWhite),
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category
            Category(
                title = category,
                isSelected = isSelected,
                onClick = {
                    selectedCategory = category
                },
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
    onCategorySelected(selectedCategory)
}

@Composable
fun Table(first: String, second: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            color = Color.Black.copy(alpha = 0.4f),
            text = first,
            modifier = Modifier.weight(0.35f)
        )
        Text(
            text = second,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun Sections(modifier: Modifier, selectedCategory: String, pokemon: DetailedPokemon) {
    when (selectedCategory) {
        "About" -> AboutSection(pokemon, modifier)
        "Stats" -> StatsSection(pokemon.stats, modifier)
        "Moves" -> MovesSection(pokemon.moves)
        "Evolution" -> EvolutionSection(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .fillMaxWidth(),
            pokemon
        )
    }
}

@Composable
fun AboutSection(
    pokemon: DetailedPokemon,
    modifier: Modifier
) {
    val weightInGrams: Double = pokemon.weightInGrams.toDouble()

    val pokemonWeight =
        when (pokemon.weightInGrams) {
            in 1 until 1000 -> "$weightInGrams g"
            in 1000 until 1000000 -> "${weightInGrams / 1000} kg"
            else -> "${weightInGrams / 1000000} t"
        }
    val heightInCm = pokemon.heightInCm.toDouble()
    val pokemonHeight =
        when (pokemon.heightInCm) {
            in 1 until 100 -> "$heightInCm cm"
            in 100 until 10000 -> "${heightInCm / 100} m"
            else -> "${heightInCm / 100000} km"
        }

    Column {
        Table(first = "Category", second = pokemon.category)
        Table(first = "Abilities", second = pokemon.abilities.joinToString {
            if (!it.isHidden) {
                it.name
            } else {
                "${it.name} (hidden)"
            }
        })
        Table(first = "Weight", second = pokemonWeight)
        Table(first = "Height", second = pokemonHeight)
        //Table(first = "Gender", second = "")
        Spacer(modifier.height(30.dp))
    }
    Column {
        Text(text = "Gender ratio")
        if (pokemon.genderRate > -1) {
            Table("Male", "${100 - (pokemon.genderRate / 8.0 * 100)}%")
            Table("Female", "${pokemon.genderRate / 8.0 * 100}%")
            //Table(first = "Egg cycles", second = "20 (4.884-5.140 steps)")
        } else {
            Spacer(modifier = Modifier.height(10.dp))
            Table(first = "Genderless", second = "100%");
        }
    }
    Spacer(modifier.fillMaxHeight())
}

@Composable
fun StatsSection(
    stats: PokemonStats,
    modifier: Modifier
) {
    Column {
        Table(first = "HP", second = stats.hp.toString())
        Table(first = "Attack", second = stats.attack.toString())
        Table(first = "Defense", second = stats.defense.toString())
        Table(first = "Sp.Atk", second = stats.specialAttack.toString())
        Table(first = "Sp.Def", second = stats.specialDefense.toString())
        Table(first = "Speed", second = stats.speed.toString())
        Divider(Modifier.width(150.dp))
        Spacer(Modifier.height(5.dp))
        Table(first = "Total", second = stats.total.toString())
    }
    Spacer(modifier.fillMaxHeight())
}

@Composable
fun MovesSection(
    moves: Array<DisplayMove>
) {
    val levelMoves: MutableList<LevelMove> = mutableListOf()
    val machineMoves: MutableList<MachineMove> = mutableListOf()
    val eggMoves: MutableList<EggMove> = mutableListOf()
    val tutorMoves: MutableList<TutorMove> = mutableListOf()

    for (move in moves) {
        when (move) {
            is LevelMove -> levelMoves.add(move)
            is MachineMove -> machineMoves.add(move)
            is EggMove -> eggMoves.add(move)
            is TutorMove -> tutorMoves.add(move)
        }
    }

    val moveCategories = listOf("Level", "Machine", "Egg", "Tutor")
    var selectedCategory by remember { mutableStateOf("Level") }
    Column(
        modifier = Modifier
            //.padding(horizontal = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
    {

        Box(
            modifier = Modifier
                .height(25.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(text = "Move categories", modifier = Modifier.align(Alignment.Center))
        }

        CategoryList(
            categories = moveCategories,
            onCategorySelected = { selectedCategory = it },
            initiallyChosen = selectedCategory,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column {
            when (selectedCategory) {
                "Level" -> LevelMoveBoxColumn(moveList = levelMoves)
                "Machine" -> MachineMoveBoxColumn(moveList = machineMoves)
                "Egg" -> EggMoveBoxColumn(moveList = eggMoves)
                "Tutor" -> TutorMoveBoxColumn(moveList = tutorMoves)
            }
        }
    }
}

@Composable
fun EvolutionSection(
    modifier: Modifier,
    pokemon: DetailedPokemon
) {
    val evolutionChain = remember {
        mutableStateOf(ArrayList<List<EvolutionChainPokemon>>())
    }

    val viewModel = PokemonViewModel()
    LaunchedEffect(Unit) {
        if (evolutionChain.value.isEmpty()) {
            viewModel.getEvolutionChain(pokemon.pokedexId, evolutionChain)
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        if (evolutionChain.value.isEmpty()) {
            println("Loading evolutions")
            CircularProgressIndicator(
                color = Color.Black,
            )
            return
        }
        println("Loaded ${evolutionChain.value.size} pokemons")

        for ((index, evolutions) in evolutionChain.value.iterator().withIndex()) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                for (evolution in evolutions) {
                    Row {
                        if (index > 0) {
                            arrow(
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Column {
                            EvolutionPokemonImage(
                                pokemon = evolution,
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(horizontal = 10.dp)
                            )
                            Text(
                                text = evolution.name.replaceFirstChar { it.uppercase() },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier.fillMaxHeight())
}

@Composable
fun EvolutionPokemonImage(modifier: Modifier = Modifier, pokemon: EvolutionChainPokemon) {
    Image(
        painter = rememberAsyncImagePainter(pokemon.spriteResourceId),
        contentDescription = pokemon.name,
        modifier = modifier,
    )
}

//region main components

/*@Composable
fun favoritesIconF(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .offset(0.dp, 11.dp)
            .background(
                color = pokemonType.secondaryColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.white_heart),
            contentDescription = "White heart",
            modifier = Modifier.size(30.dp)
        )
    }
}*/

@Composable
fun FavoritesIcon(
    active: Boolean,
    color: Color,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .offset(0.dp, 11.dp)
            .background(
                shape = CircleShape,
                color = color
            )
            .clickable { onClicked() },
        contentAlignment = Alignment.Center
    ) {
        val imageId =
            if (active) R.drawable.favorite_icon_active else R.drawable.favorite_icon_inactive
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "White heart",
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun backIcon(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.back_arrow),
        contentDescription = "search-icon",
        modifier = modifier,
    )
}

@Composable
fun arrow(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.front_arrow),
        contentDescription = "arrow",
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LevelMoveBoxColumn(moveList: List<LevelMove>) {
    val primaryWeight = 0.24f
    val secondaryWeight = 0.76f
    Column(modifier = Modifier.fillMaxWidth(0.98f)) {
        Row(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(topEnd = 7.dp, topStart = 7.dp),
                    color = Color(0xFFFFCC00)
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Level",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(secondaryWeight),
                text = "Move",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(primaryWeight),

                text = "Power",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Acc.",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "PP",
                textAlign = TextAlign.Center
            )
        }
//    Divider(color = Color.Black)

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            for (i in moveList.indices) {
                MoveBox(
//                modifier = modifier
//                    .size(190.dp)
//                    .padding(horizontal = 8.dp, vertical = 5.dp),
                    move = moveList[i],
//                onClicked = { onPokemonClicked(pokemons[i].name) }
                )
                Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MachineMoveBoxColumn(moveList: List<MachineMove>) {
    val primaryWeight = 0.24f
    val secondaryWeight = 0.76f
    Column(modifier = Modifier.fillMaxWidth(0.98f)) {
        Row(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(topEnd = 7.dp, topStart = 7.dp),
                    color = Color(0xFFFFCC00)
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Machine Name",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(secondaryWeight),
                text = "Move",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(primaryWeight),

                text = "Power",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Acc.",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "PP",
                textAlign = TextAlign.Center
            )
        }
//    Divider(color = Color.Black)

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            for (i in moveList.indices) {
                MoveBox(
//                modifier = modifier
//                    .size(190.dp)
//                    .padding(horizontal = 8.dp, vertical = 5.dp),
                    move = moveList[i],
//                onClicked = { onPokemonClicked(pokemons[i].name) }
                )
                Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EggMoveBoxColumn(moveList: List<EggMove>) {
    val primaryWeight = 0.24f
    val secondaryWeight = 0.76f
    Column(modifier = Modifier.fillMaxWidth(0.98f)) {
        Row(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(topEnd = 7.dp, topStart = 7.dp),
                    color = Color(0xFFFFCC00)
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Level",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(secondaryWeight),
                text = "Move",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(primaryWeight),

                text = "Power",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Acc.",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "PP",
                textAlign = TextAlign.Center
            )
        }
//    Divider(color = Color.Black)

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            for (i in moveList.indices) {
                MoveBox(
//                modifier = modifier
//                    .size(190.dp)
//                    .padding(horizontal = 8.dp, vertical = 5.dp),
                    move = moveList[i],
//                onClicked = { onPokemonClicked(pokemons[i].name) }
                )
                Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TutorMoveBoxColumn(moveList: List<TutorMove>) {
    val primaryWeight = 0.24f
    val secondaryWeight = 0.76f
    Column(modifier = Modifier.fillMaxWidth(0.98f)) {
        Row(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(topEnd = 7.dp, topStart = 7.dp),
                    color = Color(0xFFFFCC00)
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Level",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(secondaryWeight),
                text = "Move",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(primaryWeight),

                text = "Power",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Acc.",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "PP",
                textAlign = TextAlign.Center
            )
        }
//    Divider(color = Color.Black)

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            for (i in moveList.indices) {
                MoveBox(
//                modifier = modifier
//                    .size(190.dp)
//                    .padding(horizontal = 8.dp, vertical = 5.dp),
                    move = moveList[i],
//                onClicked = { onPokemonClicked(pokemons[i].name) }
                )
                Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
            }
        }
    }
}



@Composable
fun MoveBox(move: DisplayMove) {
    val primaryWeightUpper = 0.24f
    val secondaryWeightUpper = 0.76f

    val primaryWeightBottom = 0.4f
    val secondaryWeightBottom = 0.6f


    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (move is LevelMove) {
                Text(
                    modifier = Modifier.weight(primaryWeightUpper),
                    text = move.level.toString(),

                    textAlign = TextAlign.Center
                )
            } else if (move is MachineMove) {
                Text(
                    modifier = Modifier.weight(primaryWeightUpper),
                    text = move.machineId,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier.weight(secondaryWeightUpper),
                text = move.name,
                textAlign = TextAlign.Center
            )
            val power: String
            if (move.power == null) {
                power = "-"
            } else {
                power = move.power.toString()
            }
            Text(
                modifier = Modifier.weight(primaryWeightUpper),

                text = power,
                textAlign = TextAlign.Center
            )

            val accuracy: String
            if (move.accuracy == null) {
                accuracy = "-"
            } else {
                accuracy = move.accuracy.toString()
            }
            Text(
                modifier = Modifier.weight(primaryWeightUpper),
                text = accuracy,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeightUpper),
                text = move.pp.toString(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        Row()
        {
            Box(
                modifier = Modifier
                    .weight(secondaryWeightBottom)
                    .background(
                        shape = RoundedCornerShape(15.dp),
                        color = move.type.primaryColor
                    ), contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = move.type.name,
                    // todo
                    fontSize = 12.sp, color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .weight(primaryWeightBottom)
                    .background(
                        shape = RoundedCornerShape(15.dp),
                        color = move.damageClass.color
                    ), contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = move.damageClass.name,
                    // todo
                    fontSize = 12.sp, color = Color.White
                )
            }
        }
    }
}

@Composable
fun LargerPokemonTypeBox(modifier: Modifier = Modifier, pokemonType: PokemonType) {
    Box(
        modifier = modifier.background(
            color = pokemonType.primaryColor,
            shape = RoundedCornerShape(15.dp)
        ), contentAlignment = Alignment.Center

    ) {
        val name = if (pokemonType == PokemonType.NONE) "" else pokemonType.name
        Text(
            text = capitalizeFirstLetter(name),
            // todo
            fontSize = 17.sp, color = Color.White
        )
    }
}