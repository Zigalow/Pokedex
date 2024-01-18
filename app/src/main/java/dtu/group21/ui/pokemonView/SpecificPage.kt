package dtu.group21.ui.pokemonView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dtu.group21.data.PokedexViewModel
import dtu.group21.data.Resource
import dtu.group21.models.api.PokemonViewModel
import dtu.group21.data.pokemon.DetailedPokemon
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.models.pokemon.EvolutionChainPokemon
import dtu.group21.data.pokemon.PokemonStats
import dtu.group21.data.pokemon.PokemonType
import dtu.group21.data.pokemon.moves.DisplayMove
import dtu.group21.data.pokemon.moves.LevelMove
import dtu.group21.data.pokemon.moves.MachineMove
import dtu.group21.data.pokemon.moves.TutorMove
import dtu.group21.data.pokemon.moves.EggMove
import dtu.group21.data.pokemon.moves.EggTutorMove
import dtu.group21.ui.frontpage.capitalizeFirstLetter
import dtu.group21.ui.frontpage.formatPokemonId
import dtu.group21.ui.theme.LightWhite

@Composable
fun SpecificPage(pokedexId: Int,
                 onNavigateBack: () -> Unit,
                 onEvolutionBack: (String) -> Unit) {
    val displayPokemon = remember{
        mutableStateOf<Resource<DisplayPokemon>>(Resource.Loading)
    }
    val details = remember {
        mutableStateOf<Pair<Resource<DetailedPokemon>, Boolean>>(Pair(Resource.Loading, false))
    }

    LaunchedEffect(Unit) {
        PokedexViewModel.getPokemon(pokedexId, displayPokemon)
        PokedexViewModel.getDetails(pokedexId, details)
    }

    if (displayPokemon.value !is Resource.Success) {
        CircularProgressIndicator(
            color = Color.Black
        )
        return
    }
    
    val (pokemonResource, wasFavorite) = details.value
    println(displayPokemon.value)
    
    Inspect(
        pokemonResourceFast = displayPokemon.value,
        pokemonResource = pokemonResource,
        isFavorite = wasFavorite,
        onNavigateBack = onNavigateBack,
        onFavorited = {
            if (pokemonResource !is Resource.Success)
                return@Inspect

            val pokemon = pokemonResource.data
            val isFavorite = !wasFavorite

            if (isFavorite) {
                PokedexViewModel.makeFavorite(pokemon)
            }
            else {
                PokedexViewModel.removeFavorite(pokemon)
            }

            val oldDetails = details.value
            details.value = Pair(oldDetails.first, isFavorite)
        },
        onEvolutionBack = {
            println("Navigating to 'pokemon/$it'")
            onEvolutionBack("pokemon/$it")
        }
    )
}

@Composable
fun Inspect(
    pokemonResourceFast: Resource<DisplayPokemon>,
    pokemonResource: Resource<DetailedPokemon>,
    isFavorite: Boolean,
    onNavigateBack: () -> Unit,
    onFavorited: () -> Unit,
    onEvolutionBack: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (pokemonResourceFast !is Resource.Success)
        return

    val fastPokemon = pokemonResourceFast.data

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(fastPokemon.primaryType.secondaryColor)
    }
    Column(
        modifier
            .background(color = fastPokemon.primaryType.secondaryColor)
            .fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            Top(
                pokemon = pokemonResource,
                isFavorite = isFavorite,
                onClickBack = onNavigateBack,
                onFavorited = onFavorited
            )
            Mid(
                fastPokemon = fastPokemon,
                modifier = modifier
            )
        }
        Column(
            modifier
                .fillMaxSize()
                .background(color = fastPokemon.primaryType.secondaryColor)
        ) {
            Bottom( slowPokemon = pokemonResource,
                    fastPokemon = pokemonResourceFast,
                    onEvolutionBack = onEvolutionBack)
        }
    }

}

@Composable
fun Top(
    pokemon: Resource<DetailedPokemon>,
    isFavorite: Boolean,
    onClickBack: () -> Unit,
    onFavorited: () -> Unit,
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
        Box(
            modifier = Modifier
                .weight(0.1f)
                .size(60.dp)
                .offset(-8.dp, 11.dp)
        ) {
            if(pokemon !is Resource.Success){
                CircularProgressIndicator(
                    color = Color.Black
                )
                return
            }
            FavoritesIcon(
                active = isFavorite,
                color = pokemon.data.primaryType.secondaryColor,
                onClicked = onFavorited
            )
        }
        Spacer(modifier.width(11.dp))
    }
}

@Composable
fun Mid(
    fastPokemon: DisplayPokemon,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .height(105.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier
                .height(35.dp)
                .weight(1f)
        ) {
            Spacer(modifier.width(13.dp))
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                text = fastPokemon.name.replaceFirstChar { it.uppercase() },
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 30.sp
            )
            Text(
                text = formatPokemonId(fastPokemon.pokedexId),
                fontSize = 30.sp,
                color = fastPokemon.primaryType.primaryColor
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
                        color = fastPokemon.primaryType.primaryColor,
                        shape = RoundedCornerShape(15.dp)
                    ),
                pokemonType = fastPokemon.primaryType
            )
            Spacer(modifier.width(15.dp))
            LargerPokemonTypeBox(
                modifier
                    .width(80.dp)
                    .height(29.dp)
                    .background(
                        color = fastPokemon.secondaryType.secondaryColor,
                        shape = RoundedCornerShape(15.dp)
                    ),
                pokemonType = fastPokemon.secondaryType
            )
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Bottom(
    modifier: Modifier = Modifier,
    slowPokemon: Resource<DetailedPokemon>,
    fastPokemon: Resource<DisplayPokemon>,
    onEvolutionBack: (String) -> Unit
) {
    var direction by remember {
        mutableStateOf(true)
    }
    var visible by remember {
        mutableStateOf(true)
    }
    if (fastPokemon !is Resource.Success) {
        return
    }

    val displayPokemon = fastPokemon.data
    Column {
        AnimatedVisibility(
            visible,
            modifier.align(Alignment.CenterHorizontally)) {
            AsyncImage(
                model = displayPokemon.spriteId,
                contentDescription = displayPokemon.name,
                modifier = modifier
                    .animateContentSize()
                    .fillMaxHeight(0.4f)
            )
        }

        Column(
            modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { changeY ->
                        if (changeY < 0) {
                            direction = true // dragging up
                        } else direction = false // dragging down
                    },
                    onDragStopped = { velocity ->
                        //  negative velocity = drag up
                        //  positive velocity = drag down
                        println(velocity)
                        if (velocity == 0.0f && visible) {
                            visible = false
                        } else if (!direction && !visible && velocity == 0.0f) {
                            visible = true
                        } else if (direction) {
                            visible = false
                        } else if (!direction) {
                            visible = true
                        }

                    }
                ), verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier.height(8.dp))
            Box(
                modifier
                    .height(4.dp)
                    .width(60.dp)
                    .align(Alignment.CenterHorizontally)
                    .alpha(0.4f),

            ){
                DragIcon()
            }
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
                        if(slowPokemon !is Resource.Success){
                            Box {
                                CircularProgressIndicator(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .align(Alignment.TopCenter)
                                        .padding(30.dp),
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            return
                        }
                        Sections(
                            selectedCategory = selectedCategory,
                            pokemon = slowPokemon.data,
                            onEvolutionBack = onEvolutionBack,
                            modifier = modifier
                        )
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
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
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = second,
            )

            if (first.equals("Male") || first.equals("Female")) {
                Spacer(modifier = Modifier.weight(0.05f))
                val imageId =
                    if (first.equals("Male")) R.drawable.male_icon else R.drawable.female_icon

                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = "Pokemon Gender",
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.9995f))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun StatsBar(
    first: String, second: String, max: Int, animDuration: Int = 1000, animDelay: Int = 0
) {
    val percentage = (second.toFloat() / max)
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            percentage
        } else 0f, animationSpec = tween(
            animDuration, animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    var boxColor = Color.Gray

    if (first == "Total") {
        boxColor = if (second.toInt() in 0 until 360/*< max * 0.2*/) {
            Color(0xFFFF0000)
        } else if (second.toInt() in 360 until 480/*>= max * 0.2 && second.toInt() < max * 0.4*/) {
            Color(0xFFFFB800)
        } else if (second.toInt() in 480 until 600/*>= max * 0.4 && second.toInt() < max * 0.6*/) {
            Color(0xFFA0E515)
        } else if (second.toInt() in 600 until 670/*>= max * 0.6 && second.toInt() < max * 0.8*/) {
            Color(0xFF23CD5E)
        } else {
            Color(0xFF00E1FF)
        }
    } else {
        boxColor = if (second.toInt() in 0 until 50) {
            Color(0xFFFF0000)
        } else if (second.toInt() in 50 until 90) {
            Color(0xFFFF9800)
        } else if (second.toInt() in 90 until 120) {
            Color(0xFFA0E515)
        } else if (second.toInt() in 120 until 150) {
            Color(0xFF23CD5E)
        } else {
            Color(0xFF00E1FF)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            color = Color.Black.copy(alpha = 0.4f),
            text = first,
            modifier = Modifier.weight(0.15f)
        )
        Text(
            text = second,
            modifier = Modifier.weight(0.1f)
        )
        Box(
            modifier = Modifier
                .weight(0.50f)
                .height(5.dp)
                .background(shape = RoundedCornerShape(15.dp), color = Color(0xFFD9D9D9))
                .align(Alignment.CenterVertically)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(currentPercent.value)
                    .height(5.dp)
                    .background(shape = RoundedCornerShape(15.dp), color = boxColor)
            )
        }
        Spacer(modifier = Modifier.weight(0.02f))
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun Sections(modifier: Modifier,
             selectedCategory: String,
             pokemon: DetailedPokemon,
             onEvolutionBack: (String) -> Unit) {
    when (selectedCategory) {
        "About" -> AboutSection(pokemon)
        "Stats" -> StatsSection(pokemon.stats, modifier)
        "Moves" -> MovesSection(pokemon.moves, pokemon.name)
        "Evolution" -> EvolutionSection(
            pokemon = pokemon,
            onEvolutionBack = onEvolutionBack,
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
fun AboutSection(
    pokemon: DetailedPokemon,
    modifier: Modifier = Modifier,
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

    val pokemonGeneration =
        when (pokemon.pokedexId) {
            in 1..151 -> "Generation 1"
            in 152..251 -> "Generation 2"
            in 252..386 -> "Generation 3"
            in 387..493 -> "Generation 4"
            in 494..649 -> "Generation 5"
            in 650..721 -> "Generation 6"
            in 722..809 -> "Generation 7"
            in 810..905 -> "Generation 8"
            else -> "Generation 9"
        }

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Column {
                Table(first = "Category", second = pokemon.category)
                Table(
                    first = "Abilities",
                    second = pokemon.abilities.joinToString {
                        if (!it.isHidden) {
                            it.name
                        } else {
                            "${it.name} (hidden)"
                        }
                    }
                )
                Table(first = "Weight", second = pokemonWeight)
                Table(first = "Height", second = pokemonHeight)
                Table(first = "Introduced", second = pokemonGeneration)

                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        item {
            Column {
                Text(text = "Gender ratio")
                if (pokemon.genderRate > -1) {
                    Table("Male", "${100 - (pokemon.genderRate / 8.0 * 100)}%")
                    Table("Female", "${pokemon.genderRate / 8.0 * 100}%")
                    // Table(first = "Egg cycles", second = "20 (4.884-5.140 steps)")
                } else {
                    Spacer(modifier = Modifier.height(10.dp))
                    Table(first = "Genderless", second = "100%")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.fillMaxHeight())
        }
    }
}

@Composable
fun StatsSection(
    stats: PokemonStats,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            StatsBar(first = "HP", second = stats.hp.toString(), 255)
        }
        item {
            StatsBar(first = "Attack", second = stats.attack.toString(), 255, 1000, 100)
        }
        item {
            StatsBar(first = "Defense", second = stats.defense.toString(), 255, 1000, 200)
        }
        item {
            StatsBar(first = "Sp.Atk", second = stats.specialAttack.toString(), 255, 1000, 300)
        }
        item {
            StatsBar(first = "Sp.Def", second = stats.specialDefense.toString(), 255, 1000, 400)
        }
        item {
            StatsBar(first = "Speed", second = stats.speed.toString(), 255, 1000, 500)
        }
        item {
            Row {
                Spacer(modifier = Modifier.weight(0.0001f))
                Divider(Modifier.weight(0.5f))
                Spacer(modifier = Modifier.weight(0.01f))
            }
        }
        item {
            Spacer(Modifier.height(5.dp))
        }
        item {
            StatsBar(first = "Total", second = stats.total.toString(), 720, 1000, 600)
        }
    }
    Spacer(Modifier.fillMaxHeight())
}

@Composable
fun MovesSection(
    moves: Array<DisplayMove>,
    pokemonName: String
) {
    val levelMoves: MutableList<LevelMove> = mutableListOf()
    val initialMachineMoves: MutableList<MachineMove> = mutableListOf()
    val eggMoves: MutableList<EggMove> = mutableListOf()
    val tutorMoves: MutableList<TutorMove> = mutableListOf()

    for (move in moves) {
        when (move) {
            is LevelMove -> levelMoves.add(move)
            is MachineMove -> initialMachineMoves.add(move)
            is EggMove -> eggMoves.add(move)
            is TutorMove -> tutorMoves.add(move)
        }
    }
    levelMoves.sortBy { it.level }
    val machineMoves = sortMachineMoves(initialMachineMoves)
    eggMoves.sortBy { it.name }
    tutorMoves.sortBy { it.name }

    val moveCategories = listOf("Level", "Machine", "Egg", "Tutor")
    var selectedCategory: String by remember { mutableStateOf("Level") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

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
        Spacer(modifier = Modifier.height(15.dp))
        Column {
            val commonModifier = Modifier
                .shadow(2.2.dp)
                .background(Color(0xFFFBFBFB))

            when (selectedCategory) {
                "Level" -> {
                    if (levelMoves.isEmpty()) {
                        NoMovesTextBox(
                            modifier = commonModifier, category = "level", pokemonName = pokemonName
                        )
                    } else {
                        LevelMoveBoxColumn(modifier = commonModifier, moveList = levelMoves)
                    }
                }

                "Machine" -> {
                    if (machineMoves.isEmpty()) {
                        NoMovesTextBox(
                            modifier = commonModifier,
                            category = "machine",
                            pokemonName = pokemonName
                        )
                    } else {
                        MachineMoveBoxColumn(
                            modifier = commonModifier, moveList = machineMoves
                        )
                    }
                }

                "Egg" -> {
                    if (eggMoves.isEmpty()) {
                        NoMovesTextBox(
                            modifier = commonModifier, category = "egg", pokemonName = pokemonName
                        )
                    } else {
                        EggTutorMoveBoxColumn(modifier = commonModifier, moveList = eggMoves)
                    }
                }

                "Tutor" -> {
                    if (tutorMoves.isEmpty()) {
                        NoMovesTextBox(
                            modifier = commonModifier, category = "tutor", pokemonName = pokemonName
                        )
                    } else {
                        EggTutorMoveBoxColumn(modifier = commonModifier, moveList = tutorMoves)
                    }
                }
            }
        }
    }
}

@Composable
private fun NoMovesTextBox(modifier: Modifier = Modifier, category: String, pokemonName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.98f)
            .fillMaxHeight(0.8f),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No $category moves available for $pokemonName",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

private fun sortMachineMoves(originalList: List<MachineMove>): List<MachineMove> {
    val (machineMovesWithDash, machineMovesWithoutDash) = originalList.partition {
        it.machineId.contains(
            "-"
        )
    }

    val sortedMachineMovesWithDash = machineMovesWithDash.sortedBy { it.machineId }
    val sortedMachineMovesWithoutDash = machineMovesWithoutDash.sortedBy { it.machineId.toInt() }

    return sortedMachineMovesWithDash + sortedMachineMovesWithoutDash
}

@Composable
fun EvolutionSection(
    modifier: Modifier,
    onEvolutionBack: (String) -> Unit,
    pokemon: DetailedPokemon
) {
    if (isOnline(LocalContext.current)) {
        val evolutionChain = remember {
            mutableStateOf(ArrayList<List<EvolutionChainPokemon>>())
        }
        
        LaunchedEffect(Unit) {
            if (evolutionChain.value.isEmpty()) {
                PokemonViewModel.getEvolutionChain(pokemon.pokedexId, evolutionChain)
            }
        }

        Row(
            modifier = modifier.horizontalScroll(rememberScrollState()),
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
            if (pokemon.pokedexId != 133 && pokemon.pokedexId != 236 && pokemon.pokedexId != 265) {
                for ((index, evolutions) in evolutionChain.value.iterator().withIndex()) {
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
                                    onPokemonClicked = onEvolutionBack,
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
            } else{
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
                                        onPokemonClicked = onEvolutionBack,
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

        }
        } else Text(text = "No internet connection")
        Spacer(modifier.fillMaxHeight())
}

@Composable
fun EvolutionPokemonImage(modifier: Modifier = Modifier,
                          pokemon: EvolutionChainPokemon,
                          onPokemonClicked: (String) -> Unit) {
    Image(
        painter = rememberAsyncImagePainter(pokemon.spriteResourceId),
        contentDescription = pokemon.name,
        modifier = modifier.clickable { onPokemonClicked("${pokemon.id}")  }
    )
}
@Composable
fun handleVariationLayout(pokemon: DetailedPokemon) {
    when (pokemon.pokedexId) {
        133 -> {

            // Layout for Pokémon with pokedexId 133
            // ...
        }
        236 -> {
            // Layout for Pokémon with pokedexId 236
            // ...
        }
        265 -> {
            // Layout for Pokémon with pokedexId 265
            // ...
        }
        else -> {
            Text(text = "No internet connection")
        }
    }
}
@Composable
fun layoutForEevee(evolutionOptions: List<EvolutionChainPokemon>) {

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
fun MoveBoxColumn(moveList: List<DisplayMove>) {
    val primaryWeight = 0.24f
    val secondaryWeight = 0.76f
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
    Divider(color = Color.Black)

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        maxItemsInEachRow = 2
    ) {


        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        for (i in moveList.indices) {
            moveBox(
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


@Composable
fun moveBox(move: DisplayMove) {
    val primaryWeightUpper = 0.24f
    val secondaryWeightUpper = 0.76f

    val primaryWeightBottom = 0.4f
    val secondaryWeightBottom = 0.6f

    Box {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.weight(primaryWeightUpper),
                    text = "7"/*"move.level.toString()"*/,
                    textAlign = TextAlign.Center
                )
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EggTutorMoveBoxColumn(modifier: Modifier = Modifier, moveList: List<EggTutorMove>) {
    val leftWeight = 0.8f
    val rightWeight = 0.20f
    val textSize = 15.sp
    Column(modifier = Modifier.fillMaxWidth(0.98f)) {
        MoveLabelRow(
            labels = listOf("Move", "Power", "Acc.", "PP"),
            textSize = textSize,
            leftWeight = 0.8f,
            rightWeight = 0.2f
        )

        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {

            Spacer(modifier = Modifier.height(5.dp))
            for (i in moveList.indices) {
                MoveBox(
                    move = moveList[i],
                    leftWeight = leftWeight,
                    rightWeight = rightWeight,
                )
                Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LevelMoveBoxColumn(modifier: Modifier = Modifier, moveList: List<LevelMove>) {
    val leftWeight = 0.5f
    val mostLeftWeight = 0.3f
    val rightWeight = 0.20f
    val textSize = 15.sp
    Column(modifier = Modifier.fillMaxWidth(0.98f)) {
        MoveLabelRow(
            labels = listOf("Level", "Move", "Power", "Acc.", "PP"),
            textSize = textSize,
            mostLeftWeight = mostLeftWeight,
            leftWeight = leftWeight,
            rightWeight = rightWeight
        )
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            for (i in moveList.indices) {
                MoveBox(
                    mostLeftWeight = mostLeftWeight,
                    leftWeight = leftWeight,
                    rightWeight = rightWeight,
                    move = moveList[i],
                )
                Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MachineMoveBoxColumn(modifier: Modifier = Modifier, moveList: List<MachineMove>) {
    val leftWeight = 0.5f
    val mostLeftWeight = 0.3f
    val rightWeight = 0.20f
    val textSize = 15.sp
    Column(modifier = Modifier.fillMaxWidth(0.98f)) {
        MoveLabelRow(
            labels = listOf("Machine", "Move", "Power", "Acc.", "PP"),
            textSize = textSize,
            mostLeftWeight = mostLeftWeight,
            leftWeight = leftWeight,
            rightWeight = rightWeight
        )
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            for (i in moveList.indices) {
                MoveBox(
                    mostLeftWeight = mostLeftWeight,
                    leftWeight = leftWeight,
                    rightWeight = rightWeight,
                    move = moveList[i],
                )
                Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
            }
        }
    }
}

@Composable
fun MoveBox(
    mostLeftWeight: Float = 1f, leftWeight: Float = 1f, rightWeight: Float = 1f, move: DisplayMove
) {
    val topRowTextSize = 15.sp
    val bottomRowTextSize = topRowTextSize
    val rightWeightBottom = 0.4f
    val leftWeightBottom = 0.6f


    Column {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            if (move is LevelMove) {
                Text(
                    modifier = Modifier.weight(mostLeftWeight),
                    text = move.level.toString(),
                    fontSize = topRowTextSize,
                    textAlign = TextAlign.Center
                )
            } else if (move is MachineMove) {
                Text(
                    modifier = Modifier.weight(mostLeftWeight),
                    text = move.machineId,
                    textAlign = TextAlign.Center,
                    fontSize = topRowTextSize,
                )
            }
            Text(
                modifier = Modifier.weight(leftWeight),
                text = move.name,
                textAlign = TextAlign.Center,
                fontSize = topRowTextSize,
            )
            val power: String
            if (move.power == null) {
                power = "-"
            } else {
                power = move.power.toString()
            }
            Text(
                modifier = Modifier.weight(rightWeight),

                text = power, fontSize = topRowTextSize, textAlign = TextAlign.Center
            )

            val accuracy: String
            if (move.accuracy == null) {
                accuracy = "-"
            } else {
                accuracy = move.accuracy.toString()
            }
            Text(
                modifier = Modifier.weight(rightWeight),
                text = accuracy,
                fontSize = topRowTextSize,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(rightWeight),
                text = move.pp.toString(),
                fontSize = topRowTextSize,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        shape = RoundedCornerShape(8.dp), color = move.type.primaryColor
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = move.type.name,
                    fontSize = bottomRowTextSize,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .background(
                        shape = RoundedCornerShape(8.dp), color = move.damageClass.color
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = move.damageClass.name, fontSize = bottomRowTextSize, color = Color.White
                )
            }
        }
    }
}

@Composable
fun MoveLabelRow(
    modifier: Modifier = Modifier,
    labels: List<String>,
    textSize: TextUnit,
    mostLeftWeight: Float = 1f,
    leftWeight: Float = 1f,
    rightWeight: Float = 1f
) {
    Row(
        modifier = modifier
            .height(30.dp)
            .clip(RoundedCornerShape(topEnd = 7.dp, topStart = 7.dp))
            .shadow(2.2.dp)
            .background(
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                color = Color(0xFFFFCC00)
            ), horizontalArrangement = Arrangement.Center
    ) {
        var weight: Float
        for (i in labels.indices) {
            weight = when (i) {
                0 -> {
                    if (labels.size == 5) {
                        mostLeftWeight
                    } else {
                        leftWeight
                    }
                }

                1 -> {
                    if (labels.size == 5) {
                        leftWeight
                    } else {
                        rightWeight
                    }
                }

                else -> rightWeight
            }
            Text(
                modifier = Modifier
                    .weight(weight)
                    .align(Alignment.CenterVertically),
                text = labels[i],
                textAlign = TextAlign.Center,
                fontSize = textSize
            )
        }
    }
}

@Composable
fun DragIcon(){
    Image(
        painter = painterResource(id = R.drawable.drag_icon),
        contentDescription = "drag",
        modifier = Modifier.fillMaxSize()
    )
}

/*
Special  thanks to Jorgesys for his answer on this post:
https://stackoverflow.com/questions/51141970/check-internet-connectivity-android-in-kotlin
 */
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}
