package dtu.group21.ui.favorites

//import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dtu.group21.data.PokedexViewModel
import dtu.group21.data.Resource
import dtu.group21.data.pokemon.StatPokemon
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.frontpage.PokemonTypeBox
import dtu.group21.ui.frontpage.SearchIcon
import dtu.group21.ui.frontpage.formatPokemonId
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize

@Composable
fun FavoritesPage(
    onNavigate: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: PokedexViewModel,
    onPokemonClicked: (String) -> Unit
) {
    val pokemons = remember { mutableStateOf(emptyList<Resource<StatPokemon>>()) }
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

    // Load the favorite pokemons
    LaunchedEffect(Unit) {
        viewModel.getFavoritePokemons(pokemons)
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            UpperMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
            ) {

                Box(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxWidth()
                )
                {
                    BackIcon(size = 49.dp, onClicked = { onNavigateBack() })
                }

                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                )
                {
                    Text(
                        text = "Favorites",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = bigFontSize,
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxWidth()
                )
                {
                    SearchIcon(size = 49.dp, onClicked = { onNavigate("searchFavourites") })

                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(pokemons.value.size) { index ->
                    val pokemonResource = pokemons.value[index]
                    val boxModifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                    FavoritePokemonBox(
                        modifier = boxModifier,
                        pokemonResource = pokemonResource,
                        onClicked = onPokemonClicked
                    )
                }
            }
        }
    }
}
@Composable
fun BackIcon(modifier: Modifier = Modifier, size: Dp, onClicked: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.back_arrow),
        contentDescription = "Back Arrow",
        modifier = modifier
            .padding(vertical = size / 3)
            .size(size)
            .clickable { onClicked() }
    )
}

@Composable
fun FavoritePokemonBox(
    modifier: Modifier = Modifier,
    pokemonResource: Resource<StatPokemon>,
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
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        PokemonTypeBox(
                            pokemonType = pokemon.primaryType,
                            modifier = Modifier.fillMaxSize(0.2f)
                        )
                        PokemonTypeBox(
                            pokemonType = pokemon.secondaryType,
                            modifier = Modifier.fillMaxSize(0.25f)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = formatPokemonId(pokemon.pokedexId),
                            color = pokemon.primaryType.primaryColor,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.padding(top = 100.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = pokemon.primaryType.primaryColor,
                                shape = RoundedCornerShape(30.dp)
                            ),

                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(
                            text = pokemon.name,
                            fontSize = 17.sp,
                            color = Color.White,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(
                                vertical = 3.dp,
                                horizontal = 16.dp
                            ) // Add padding as needed
                        )
                    }
                }
            }
        }
    }
}