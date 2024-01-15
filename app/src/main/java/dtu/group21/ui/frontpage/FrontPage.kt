package dtu.group21.ui.frontpage

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.data.pokemon.StatPokemon
import dtu.group21.data.PokedexViewModel
import dtu.group21.data.Resource
import dtu.group21.data.pokemon.PokemonType
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.theme.Yellow60
import java.util.Locale

@Composable
fun FrontPage(onNavigate: (String) -> Unit, pokemons: MutableState<List<Resource<StatPokemon>>>) {
    val pokemons = remember { mutableStateOf(emptyList<Resource<StatPokemon>>()) }
    LaunchedEffect(Unit) {
        val pokedexViewModel = PokedexViewModel()
        val ids = (1..50)
        pokedexViewModel.getPokemons(ids.toList(),pokemons)
    }

    var menuIsOpen by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }
    Box {
        Column {
            UpperMenu(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth()) {
                    MenuIcon(
                        size = 49.dp,
                        onClicked = { menuIsOpen = true })
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    PokemonLogo(size = 174.dp)
                }

                Box(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    SearchIcon(size = 49.dp, onClicked = { onNavigate("search") })
                }

            }
            Spacer(modifier = Modifier.padding(3.dp))
            PokemonColumn(
                pokemons = pokemons.value,
                onPokemonClicked = {
                    println("Navigating to 'pokemon/$it'")
                    onNavigate("pokemon/$it")
                },
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
        FavoritesIcon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 5.dp, bottom = 5.dp)
                .size(70.dp),
            onClicked = {
                onNavigate("favorites")
            }
        )


        if (menuIsOpen) {
            Column(
                modifier = Modifier.width(80.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Menu(
                        modifier = Modifier
                            .fillMaxHeight(),
                    ) {
                        MenuIcon(size = 49.dp, onClicked = { menuIsOpen = false })
                        SettingsIcon(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(60.dp),
                            onClicked = {
                                onNavigate("settings")
                            }
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .background(
                color = Yellow60,
            )
    ) {
        content()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonColumn(
    modifier: Modifier = Modifier,
    pokemons: List<Resource<DisplayPokemon>>,
    onPokemonClicked: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val chunks = if (screenWidth > 600.dp) 4 else 2
    val itemWidth = (screenWidth / chunks) - 6.dp //-6.dp to consider patting in between each box
    val alignedPokemons = pokemons.chunked(chunks)

    LazyColumn(modifier.fillMaxWidth()) {
        items(alignedPokemons.size) { index ->
            Row(
                modifier
                    .fillMaxWidth()
            ) {
                for (pokemonResource in alignedPokemons[index]) {
                    PokemonBox(
                        modifier = Modifier
                            .width(itemWidth)
                            .aspectRatio(1f)
                            .padding(2.dp),
                        pokemonResource = pokemonResource,
                        onClicked = onPokemonClicked
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsIcon(modifier: Modifier = Modifier, onClicked: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onClicked() },
        contentAlignment = Alignment.Center
    )
    {
        Image(
            painter = painterResource(id = R.drawable.settings_icon),
            contentDescription = "settings-icon",
            modifier = Modifier.fillMaxSize(0.56f)
        )
    }
}

@Composable
fun FavoritesIcon(modifier: Modifier = Modifier, onClicked: () -> Unit) {
    Box(
        modifier = modifier
            .background(
                shape = CircleShape,
                color = Color(0xFFDE4A4A),
            )
            .clickable { onClicked() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.favorite_icon_active),
            contentDescription = "White heart",
            modifier = Modifier.fillMaxSize(0.56f)
        )
    }
}
//endregion

//helper functions to components

//region upper menu component functions
@Composable
fun PokemonLogo(modifier: Modifier = Modifier, size: Dp) {
    Image(
        painter = painterResource(id = R.drawable.pokemon_logo),
        contentDescription = "Pokemon logo",
        modifier = modifier
            .height(size / 1.75f)
            .width(size)
//                .height(87½½.dp)
//                .width(154.dp)
    )
}

@Composable
fun SearchIcon(modifier: Modifier = Modifier, size: Dp, onClicked: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.search_icon),
        contentDescription = "search icon",
        modifier = modifier
            .padding(vertical = size / 3)
            .size(size)
            .clickable { onClicked() }
//                .padding(vertical = 16.dp, horizontal = 19.dp)
//                .size(49.dp)
    )
}

@Composable
fun MenuIcon(modifier: Modifier = Modifier, size: Dp, onClicked: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.menu_icon), // Replace with your image resource
        contentDescription = "menu-icon", // Set to null if the image is decorative
        modifier = modifier
            .padding(vertical = size / 3)
            .size(size)
            .clickable { onClicked() }
//                .padding(vertical = 16.dp, horizontal = 19.dp)
//                .size(49.dp),

    )
}
//endregion

//region pokemon column functions
@Composable
fun PokemonTypeBox(modifier: Modifier = Modifier, pokemonType: PokemonType) {
    Box(
        modifier = modifier.background(
            color = pokemonType.primaryColor,
            shape = RoundedCornerShape(15.dp)
        ), contentAlignment = Alignment.Center

    ) {
        val name = if (pokemonType == PokemonType.NONE) "" else pokemonType.name
        Text(
            text = name,
            // todo
            fontSize = 11.sp, color = Color.White
        )
    }
}

@Composable
fun PokemonImage(modifier: Modifier = Modifier, pokemon: DisplayPokemon) {
    AsyncImage(
        model = pokemon.spriteId,
        contentDescription = pokemon.name,
        modifier = modifier.animateContentSize()
    )
    /*Image(
            painter = rememberAsyncImagePainter(pokemon.spriteResourceId),
            contentDescription = pokemon.species.name,
            modifier = modifier,
        )*/
}

fun capitalizeFirstLetter(text: String) = text.lowercase(Locale.ROOT)
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

fun formatPokemonId(unformattedNumber: Int): String {
    return "#${"%04d".format(unformattedNumber)}"
}

@Composable
fun PokemonBox(
    modifier: Modifier = Modifier,
    pokemonResource: Resource<DisplayPokemon>,
    onClicked: (String) -> Unit
) {
    when (pokemonResource) {
        is Resource.Failure -> {
            // TODO: handle?
        }

        Resource.Loading -> {
            CircularProgressIndicator(
                modifier = modifier,
                color = Color.Black
            )
        }

        is Resource.Success -> {
            val pokemon = pokemonResource.data

            Box(
                modifier = modifier
                    .clickable { onClicked("${pokemon.pokedexId}") }
                    .background(
                        color = pokemon.primaryType.secondaryColor,
                        shape = RoundedCornerShape(20.dp)
                    )

            ) {
                PokemonImage(modifier = Modifier.align(Alignment.BottomEnd), pokemon = pokemon)
                Column {
                    Spacer(modifier = Modifier.height(7.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.width(7.dp))
                        val pokemonTypeBoxModifier = Modifier.weight(1f)
                        PokemonTypeBox(
                            pokemonType = pokemon.primaryType,
                            modifier = pokemonTypeBoxModifier
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        PokemonTypeBox(
                            pokemonType = pokemon.secondaryType,
                            modifier = pokemonTypeBoxModifier,
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        // For displaying pokedex number
                        Text(
                            text = formatPokemonId(pokemon.pokedexId),
                            modifier = Modifier.weight(1f),
                            color = pokemon.primaryType.primaryColor,
                            textAlign = TextAlign.End,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                    }
                    Spacer(modifier = Modifier.fillMaxHeight(0.8f))
                    Row {
                        Spacer(modifier = Modifier.width(7.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = pokemon.primaryType.primaryColor,
                                    shape = RoundedCornerShape(30.dp)
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = pokemon.name,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                fontSize = 17.sp,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}

//endregion