package dtu.group21.ui.pokemonView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import dtu.group21.data.pokemon.PokemonGender
import dtu.group21.data.pokemon.moves.PokemonMove
import dtu.group21.models.pokemon.PokemonSpecies
import dtu.group21.data.pokemon.PokemonStats
import dtu.group21.data.pokemon.PokemonType
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.frontpage.capitalizeFirstLetter
import dtu.group21.ui.frontpage.formatPokemonId
import dtu.group21.ui.theme.LightWhite


@Composable
fun SpecificPage(pokedexId: Int, onNavigateBack: () -> Unit) {
    val pokemon = remember {
        mutableStateOf<Resource<DetailedPokemon>>(Resource.Loading)
    }
    val displayPokemon = remember{
        mutableStateOf<Resource<DisplayPokemon>>(Resource.Loading)
    }

    LaunchedEffect(Unit) {
        val viewModel = PokedexViewModel()
        viewModel.getDetails(pokedexId, pokemon)
        viewModel.getPokemon(pokedexId,displayPokemon)
    }
    println(displayPokemon.value)
    /*Loading(
        pokemonResource = displayPokemon.value,
        modifier = Modifier
    )*/
    Inspect(
        pokemonResource = pokemon.value,
        pokemonResourceFast = displayPokemon.value,
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun Inspect(
    pokemonResource: Resource<DetailedPokemon>,
    pokemonResourceFast: Resource<DisplayPokemon>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    if (pokemonResourceFast !is Resource.Success) return

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
                onClickBack = onNavigateBack
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
            Bottom(slowPokemon = pokemonResource, fastPokemon = pokemonResourceFast)
        }
    }

}

@Composable
fun Top(
    pokemon: Resource<DetailedPokemon>,
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
            if(pokemon !is Resource.Success){
                CircularProgressIndicator(
                    color = Color.Black
                )
                return
            }
            FavoritesIcon(
                active = pokemon.data.isFavorite.value,
                color = pokemon.data.primaryType.secondaryColor,
                onClicked = {
                    // TODO
                }

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
                    ), pokemonType = fastPokemon.primaryType
            )
            Spacer(modifier.width(15.dp))
            LargerPokemonTypeBox(
                modifier
                    .width(80.dp)
                    .height(29.dp)
                    .background(
                        color = fastPokemon.secondaryType.secondaryColor,
                        shape = RoundedCornerShape(15.dp)
                    ), pokemonType = fastPokemon.secondaryType
            )
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Bottom(modifier: Modifier = Modifier, slowPokemon: Resource<DetailedPokemon>, fastPokemon: Resource<DisplayPokemon>) {
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
                drag()
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
                            CircularProgressIndicator(
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            return
                        }
                        Sections(
                            selectedCategory = selectedCategory,
                            pokemon = slowPokemon.data,
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
        Text(
            text = second,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun StatsBar(first: String, second: String) {
    val percentage = (second.toFloat() / 100).coerceIn(0f, 1f)
    val boxColor =
        if (second.toFloat() < 50) Color(0xFFFF0000) else if (second.toFloat() >= 50 && second.toFloat() < 80) Color(
            0xFFFFB800
        ) else Color(0xFF42FF00)
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
            modifier = Modifier.weight(0.05f)
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
                    .fillMaxWidth(percentage)
                    .height(5.dp)
                    .background(shape = RoundedCornerShape(15.dp), color = boxColor)
            )
        }
        Spacer(modifier = Modifier.weight(0.02f))
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
        Table(first = "Introduced", second = pokemonGeneration)

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
        StatsBar(first = "HP", second = stats.hp.toString())
        StatsBar(first = "Attack", second = stats.attack.toString())
        StatsBar(first = "Defense", second = stats.defense.toString())
        StatsBar(first = "Sp.Atk", second = stats.specialAttack.toString())
        StatsBar(first = "Sp.Def", second = stats.specialDefense.toString())
        StatsBar(first = "Speed", second = stats.speed.toString())
        Divider(Modifier.width(150.dp))
        Spacer(Modifier.height(5.dp))
        Table(first = "Total", second = stats.total.toString())
    }
    Spacer(modifier.fillMaxHeight())
}

@Composable
fun MovesSection(
    moves: Array<PokemonMove>
) {
    Column {
        MoveBoxColumn(moveList = moves.toList())
    }
}

@Composable
fun EvolutionSection(
    modifier: Modifier,
    pokemon: DetailedPokemon
) {
    if (isOnline(LocalContext.current)) {
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
fun EvolutionPokemonImage(modifier: Modifier = Modifier, pokemon: EvolutionChainPokemon) {
    Image(
        painter = rememberAsyncImagePainter(pokemon.spriteResourceId),
        contentDescription = pokemon.name,
        modifier = modifier,
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
@Composable
fun drag (){
    Image(
        painter = painterResource(id = R.drawable.drag_icon),
        contentDescription = "drag",
        modifier = Modifier.fillMaxSize()
        )
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoveBoxColumn(moveList: List<PokemonMove>) {
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
fun moveBox(move: PokemonMove) {
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
