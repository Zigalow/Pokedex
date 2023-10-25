package dtu.group21.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dtu.group21.ui.theme.PokedexTheme
import com.example.pokedex.R
import dtu.group21.models.pokemon.Pokemon
import dtu.group21.models.pokemon.PokemonGender
import dtu.group21.models.pokemon.PokemonStats
import dtu.group21.models.pokemon.PokemonType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                /*   // A surface container using the 'background' color from the theme
                   Column {
                       UpperMenu()
                       // Line
                       Divider(thickness = 1.dp, color = Color.Black)
                       Spacer(modifier = Modifier.padding(18.dp))
                   }*/
                pokemonBox(pokemon = bulbasaur)

            }
        }
    }


    val bulbasaur = Pokemon(
        "bulbasaur",
        1,
        PokemonType.GRASS,
        PokemonType.POISON,
        PokemonGender.MALE,
        PokemonStats(12, 12, 12, 12, 12, 12),
        R.drawable._0001
    )

    @Composable
    fun UpperMenu(modifier: Modifier = Modifier) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(83.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.menu_icon), // Replace with your image resource
                    contentDescription = "menu-icon", // Set to null if the image is decorative
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 19.dp)
                        .size(49.dp),
                    //contentScale = ContentScale.FillBounds
                )/*Box(
                modifier = modifier
                    .padding(vertical = 16.dp, horizontal = 19.dp)
                    .height(49.dp)
                    .width(49.dp)
                    .background(color = Color.Gray)

            )*/
                Spacer(
                    modifier = modifier.padding(horizontal = 14.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.pokemon_logo), // Replace with your image resource
                    contentDescription = "Pokemon logo", // Set to null if the image is decorative
                    modifier = Modifier
                        .height(87.dp)
                        .width(154.dp)
                )/* Box(
                 modifier = modifier
                     .height(87.dp)
                     .width(154.dp)
                     .background(color = Color.Gray)
             )*/
                Spacer(
                    modifier = modifier.padding(horizontal = 14.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "search icon",
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 19.dp)
                        .size(49.dp)

                )
            }

        }


    }

    @Composable
    fun typeBox(modifier: Modifier = Modifier, pokemonType: PokemonType) {
        Box(
            modifier = modifier
                .width(50.dp)
                .height(18.dp)
                .background(
                    Color(android.graphics.Color.parseColor(pokemonType.regularColorHexvalue)),
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = pokemonType.name.toLowerCase().capitalize(),
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }

    @Composable
    fun nameBox(modifier: Modifier = Modifier, pokemonType: PokemonType) {
        Box(
            modifier = modifier
                .width(95.dp)
                .height(23.dp)
                .background(
                    Color(android.graphics.Color.parseColor(pokemonType.regularColorHexvalue)),
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = pokemonType.name.toLowerCase().capitalize(),
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }

    fun formatPokemonId(unformattedNumber: Int): String {
        return "#${"%04d".format(unformattedNumber)}"
    }

    @Composable
    fun pokemonBox(modifier: Modifier = Modifier, pokemon: Pokemon) {
        Box(
            modifier = modifier
                .size(174.dp)
                .background(
                    Color(android.graphics.Color.parseColor(pokemon.type.backgroundColorHexvalue)),
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Image(
                painter = painterResource(id = pokemon.spriteResourceId),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(174.dp)
                    .background(
                        Color(android.graphics.Color.parseColor(pokemon.type.backgroundColorHexvalue)),
                        shape = RoundedCornerShape(15.dp)
                    )
            )
            Row(
                modifier = modifier
                    .padding(horizontal = 4.dp, 11.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                typeBox(modifier = modifier, pokemonType = pokemon.type)
                if (pokemon.secondaryType != null) {
                    Spacer(modifier = modifier.padding(horizontal = 4.dp))
                    typeBox(pokemonType = pokemon.secondaryType)
                }
                Box(
                    modifier = Modifier
                        .weight(1f), // Takes up remaining space
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

            Box(
                modifier = modifier
                    .width(120.dp)
                    .height(23.dp)
                    .offset(x = 8.dp, y = 145.dp)
                    .background(
                        Color(android.graphics.Color.parseColor(pokemon.type.regularColorHexvalue)),
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center,

            ) {
                Text(
                    text = pokemon.name.toLowerCase().capitalize(),
                    fontSize = 17.sp,
                    color = Color.White,
                )
            }

        }

    }


    @Composable
    fun pokemonColumn() {

    }
    

    @Preview(showBackground = false, showSystemUi = true)
    @Composable
    fun Preview() {
        pokemonBox(pokemon = bulbasaur)
    }
}
    
