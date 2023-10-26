package dtu.group21.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import dtu.group21.models.pokemon.Pokemon
import dtu.group21.models.pokemon.PokemonSamples
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.ui.theme.PokedexTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // FrontPage()
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Column {
                    UpperMenu(
                        leftIcon = { MenuIcon() },
                        middleIcon = { PokemonLogo() },
                        rightIcon = { SearchIcon() })
                    // Line
                    Divider(thickness = 1.dp, color = Color.Black)
                    Spacer(modifier = Modifier.padding(3.dp))
                    PokemonColumn(listOfPokemon = PokemonSamples.listOfPokemons)
                }
                FavoritesIcon(modifier = Modifier.offset(290.dp, 675.dp))
                //Menu()
            }
        }
    }

    @Composable
    fun FrontPage() {

    }

    //region main components
    @Composable
    fun UpperMenu(
        modifier: Modifier = Modifier,
        leftIcon: @Composable () -> Unit,
        middleIcon: @Composable () -> Unit,
        rightIcon: @Composable () -> Unit
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(83.dp)
        ) {
            Row {
                leftIcon()
                Spacer(
                    modifier = modifier.padding(horizontal = 14.dp)
                )
                middleIcon()
                Spacer(
                    modifier = modifier.padding(horizontal = 14.dp)
                )
                rightIcon()
            }

        }


    }

    @Composable
    fun Menu(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .width(80.dp)
                .background(Color(android.graphics.Color.parseColor("#FFCC00")))
        ) {
            MenuIcon()
            Image(
                painter = painterResource(id = R.drawable.settings_icon), // Replace with your image resource
                contentDescription = "settings-icon", // Set to null if the image is decorative
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 19.dp)
                    .size(49.dp)
                    .offset(y = 675.dp),

                )
        }

    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun PokemonColumn(modifier: Modifier = Modifier, listOfPokemon: List<Pokemon>) {
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 2
        ) {
            for (i in listOfPokemon.indices) {
                PokemonBox(
                    modifier = modifier
                        .padding(horizontal = 8.dp, vertical = 5.dp),
                    pokemon = listOfPokemon[i], size = 174.dp
                )
                //    Spacer(modifier = modifier.padding(horizontal = 12.dp))
            }
        }
        /* Column(
             modifier = modifier
                 .verticalScroll(rememberScrollState())
                 .padding(horizontal = 10.dp)
         ) {
 
             //Spacer(modifier = modifier.padding(vertical = 5.dp))
         }*/


    }


    @Composable
    fun FavoritesIcon(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .size(60.dp)
                .offset(30.dp, 30.dp)
                .background(
                    Color(android.graphics.Color.parseColor("#DE4A4A")), shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.white_heart),
                contentDescription = "White heart",
                modifier = Modifier.size(30.dp)
            )
        }
    }
//endregion

//helper functions to components

    //region upper menu component functions
    @Composable
    fun PokemonLogo(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.pokemon_logo),
            contentDescription = "Pokemon logo",
            modifier = modifier
                .height(87.dp)
                .width(154.dp)
        )
    }

    @Composable
    fun SearchIcon(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "search icon",
            modifier = modifier
                .padding(vertical = 16.dp, horizontal = 19.dp)
                .size(49.dp)

        )
    }

    @Composable
    fun MenuIcon(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.menu_icon), // Replace with your image resource
            contentDescription = "menu-icon", // Set to null if the image is decorative
            modifier = modifier
                .padding(vertical = 16.dp, horizontal = 19.dp)
                .size(49.dp),
        )
    }
//endregion

    //region pokemon column functions
    @Composable
    fun PokemonTypeBox(modifier: Modifier = Modifier, pokemonType: PokemonType) {
        Box(
            modifier = modifier
                .background(
                    color = pokemonType.primaryColor,
                    shape = RoundedCornerShape(15.dp)
                ), contentAlignment = Alignment.Center

        ) {
            Text(
                text = capitalizeFirstLetter(pokemonType.name),
                // todo
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }

    @Composable
    fun PokemonImage(modifier: Modifier = Modifier, pokemon: Pokemon) {
        Image(
            painter = painterResource(id = pokemon.spriteResourceId),
            contentDescription = pokemon.name,
            modifier = modifier
                .background(
                    color = pokemon.type.secondaryColor,
                    shape = RoundedCornerShape(20.dp)
                )
        )
    }

    private fun capitalizeFirstLetter(text: String) = text.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }


    @Composable
    fun PokemonNameBox(modifier: Modifier = Modifier, pokemon: Pokemon, size: Dp) {
        Box(
            modifier = modifier
                .background(
                    color = pokemon.type.primaryColor,
                    shape = RoundedCornerShape(size / 1.5f)
                ),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                text = capitalizeFirstLetter(pokemon.name),
                fontSize = 17.sp,
                color = Color.White,
            )
        }
    }

    private fun formatPokemonId(unformattedNumber: Int): String {
        return "#${"%04d".format(unformattedNumber)}"
    }

    @Composable
    fun PokemonBox(modifier: Modifier = Modifier, pokemon: Pokemon, size: Dp) {
        Box(
            modifier = modifier.size(size)
//                .background(
//                    Color(android.graphics.Color.parseColor(pokemon.type.regularColorHexvalue)),
//                    shape = RoundedCornerShape(50.dp)
//                )
        ) {
            PokemonImage(modifier = Modifier.size(size), pokemon = pokemon)
            Row(
                modifier = Modifier
                    .padding(horizontal = (size / 29), vertical = (size / 16))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val pokemonTypeBoxModifier = Modifier
                    .width(size / 10 * 3)
                    .height(size / 10)
                PokemonTypeBox(
                    pokemonType = pokemon.type,
                    modifier = pokemonTypeBoxModifier
                )
                if (pokemon.secondaryType != null) {
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    PokemonTypeBox(
                        modifier = pokemonTypeBoxModifier,
                        pokemonType = pokemon.secondaryType
                    )
                }
                // For displaying pokedex number
                Box(
                    modifier = Modifier.weight(1f), // Takes up remaining space
                    contentAlignment = Alignment.CenterEnd // Align the content to the end (right)
                ) {
                    Text(
                        text = formatPokemonId(pokemon.pokedexNumber),
                        textAlign = TextAlign.Right,
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
            PokemonNameBox(
                modifier = Modifier
                    .width(size / 7.5f * 5)
                    .height(size / 7.5f)
                    .offset(x = size / 7.5f / 3, y = size / 7.5f * 6.2f),
                pokemon = pokemon,
                size = size / 7.5f
            )
        }
    }

//endregion

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {

    }
}
    
