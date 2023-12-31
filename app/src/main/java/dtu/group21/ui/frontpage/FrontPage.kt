package dtu.group21.ui.frontpage

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.R
import dtu.group21.models.api.PokemonViewModel
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.ui.shared.UpperMenu
import java.util.Locale

@Composable
fun FrontPage(onNavigate: (String) -> Unit, pokemons: MutableList<MutableState<ComplexPokemon>>) {
    var menuIsOpen by remember { mutableStateOf(false) }
    /*val pokemons = remember {
        mutableListOf<MutableState<ComplexPokemon>>()
    }*/

//    val viewModel = PokemonViewModel()
    //val ids = (32..35).toList().toIntArray().toTypedArray()
    /*val ids = intArrayOf(6, 32, 35, 82, 133, 150, 668, 669).toTypedArray()
    LaunchedEffect(Unit) {
        viewModel.getPokemons(ids, pokemons)
    }*/

    //var favoritePokemon by remember { mutableStateOf(PokemonSamples.listOfPokemons.filter { it.isFavorit }) }
    Box {
        Column {
            UpperMenu(
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuIcon(
                    size = 49.dp,
                    onClicked = { menuIsOpen = true })

                Spacer(
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
                PokemonLogo(size = 174.dp)
                Spacer(
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
                SearchIcon(size = 49.dp, onClicked = { onNavigate("search") })
            }
            Spacer(modifier = Modifier.padding(3.dp))
            PokemonColumn(
                pokemons = pokemons,
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
                modifier = Modifier
                    .width(80.dp)) {
                Menu(
                    modifier = Modifier
                        .fillMaxHeight(),
                ) {
                    MenuIcon(size = 49.dp, onClicked = { menuIsOpen = false })
                }
            }
            Box(modifier = Modifier.width(90.dp)
                .fillMaxHeight()){
                SettingsIcon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 5.dp)
                        .size(70.dp),
                    onClicked = {
                        onNavigate("settings")
                    }
                )
            }
        }
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFFFFCC00),
            )
    ) {
        content()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonColumn(
    modifier: Modifier = Modifier,
    pokemons: List<MutableState<ComplexPokemon>>,
    onPokemonClicked: (String) -> Unit
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        maxItemsInEachRow = 2
    ) {
        val boxModifier = modifier
            .size(180.dp)
            .padding(horizontal = 4.dp, vertical = 5.dp)
        for (i in pokemons.indices) {
            val pokemon = pokemons[i].value
            if (pokemon.id == 0) {
                CircularProgressIndicator(
                    modifier = boxModifier,
                    color = Color.Black
                )
            } else if (pokemon.id == -1) {
                // fail
            } else {
                PokemonBox(
                    modifier = boxModifier,
                    pokemon = pokemon,
                    onClicked = { onPokemonClicked("${pokemon.id}") }
                )
            }
        }
    }
}

@Composable
fun SettingsIcon(modifier: Modifier = Modifier, onClicked: () -> Unit){
    Box(modifier = modifier
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
            painter = painterResource(id = R.drawable.white_heart),
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
            .padding(vertical = size / 3, horizontal = size / 2.5f)
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
            .padding(vertical = size / 3, horizontal = size / 2.5f)
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
            text = capitalizeFirstLetter(name),
            // todo
            fontSize = 11.sp, color = Color.White
        )
    }
}

@Composable
fun PokemonImage(modifier: Modifier = Modifier, pokemon: ComplexPokemon) {
    AsyncImage(
        model = pokemon.spriteResourceId,
        contentDescription = pokemon.species.name,
        modifier = modifier
    )
    /*Image(
            painter = rememberAsyncImagePainter(pokemon.spriteResourceId),
            contentDescription = pokemon.species.name,
            modifier = modifier,
        )*/
}

fun capitalizeFirstLetter(text: String) = text.lowercase(Locale.ROOT)
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }


@Composable
fun PokemonNameBox(modifier: Modifier = Modifier, pokemon: ComplexPokemon, size: Dp) {
    Box(
        modifier = modifier.background(
            color = pokemon.type.primaryColor,
            shape = RoundedCornerShape(30.dp)
        ),
    ) {
        Text(
            text = capitalizeFirstLetter(pokemon.species.name),
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 17.sp,
            color = Color.White,
        )
    }
}

fun formatPokemonId(unformattedNumber: Int): String {
    return "#${"%04d".format(unformattedNumber)}"
}

@Composable
fun PokemonBox(modifier: Modifier = Modifier, pokemon: ComplexPokemon, onClicked: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onClicked() }
            .background(
                color = pokemon.type.secondaryColor,
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
                    pokemonType = pokemon.type,
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
                    text = formatPokemonId(pokemon.id),
                    modifier = Modifier.weight(1f),
                    color = pokemon.type.primaryColor,
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
                            color = pokemon.type.primaryColor,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = capitalizeFirstLetter(pokemon.species.name),
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        fontSize = 17.sp,
                        color = Color.White,
                    )
                }
                /*PokemonNameBox(
                    modifier = Modifier
                        .weight(1f),
                    pokemon = pokemon,
                    size = size / 7.5f
                )*/
            }
        }
    }
}


//endregion