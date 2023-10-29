package dtu.group21.ui.favorit

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import dtu.group21.models.pokemon.Pokemon
import dtu.group21.models.pokemon.PokemonSamples.listOfPokemons
import dtu.group21.ui.frontpage.PokemonBox
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.frontpage.PokemonTypeBox
import dtu.group21.ui.frontpage.capitalizeFirstLetter
import dtu.group21.ui.frontpage.formatPokemonId


@Composable
fun FavoritesPage(favoritePokemons: List<Pokemon>, onPokemonClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackIcon(
                size = 30.dp,
                onClicked = {true},
            )

            Text(
                text = "Favorites",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = 1.dp, end = 15.dp),
                textAlign = TextAlign.Center
            )
        }

        favoritePokemons.forEach { pokemon ->
            FavoritPokemonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                pokemon = pokemon,
                onClicked = { onPokemonClicked(pokemon.name) }
            )
        }
    }
}
@Composable
fun BackIcon(modifier: Modifier = Modifier, size: Dp, onClicked: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.back_button), // Replace with your image resource
        contentDescription = "back-icon", // Set to null if the image is decorative
        modifier = modifier
        //    .padding(vertical = size / 3, horizontal = size / 2.5f)
          //  .size(size)
            .clickable { onClicked() }
    )
}
@Composable
fun FavoritPokemonBox(modifier: Modifier = Modifier, pokemon: Pokemon, onClicked: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onClicked() }
            .background(
                color = pokemon.type.secondaryColor,
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
                    pokemonType = pokemon.type,
                    modifier = Modifier.fillMaxSize(0.2f)
                )
                PokemonTypeBox(
                    pokemonType = pokemon.secondaryType,
                    modifier = Modifier.fillMaxSize(0.2f)
                )
            }
            Column( modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = formatPokemonId(pokemon.pokedexNumber),
                    color = Color.White,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.padding(top = 100.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = pokemon.type.primaryColor,
                        shape = RoundedCornerShape(30.dp)
                    ),

                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = capitalizeFirstLetter(pokemon.name),
                    fontSize = 17.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 16.dp) // Add padding as needed
                )
            }
        }
    }
}
@Preview
@Composable
fun ShowFavoritePage(){
    FavoritesPage(listOfPokemons, onPokemonClicked = {})

}