package dtu.group21.ui.favorites

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
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.frontpage.PokemonTypeBox
import dtu.group21.ui.frontpage.capitalizeFirstLetter
import dtu.group21.ui.frontpage.formatPokemonId
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize


@Composable
fun FavoritesPage(
    onNavigateBack: () -> Unit,
    onPokemonClicked: (String) -> Unit,
    favoritePokemons: List<Pokemon>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        UpperMenu(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
        ) {
            Spacer(Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back Arrow",
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onNavigateBack() },
                alignment = Alignment.CenterStart,
            )
            Text(
                text = "Favorites",
                modifier = Modifier.weight(0.01f).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = bigFontSize,
            )
            Spacer(Modifier.width(45.dp))
        }

        favoritePokemons.forEach { pokemon ->
            FavoritePokemonBox(
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
fun FavoritePokemonBox(modifier: Modifier = Modifier, pokemon: Pokemon, onClicked: () -> Unit) {
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
                    modifier = Modifier.fillMaxSize(0.25f)
                )
            }
            Column( modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = formatPokemonId(pokemon.pokedexNumber),
                    color = pokemon.type.primaryColor,
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
    FavoritesPage(
        onNavigateBack = {},
        onPokemonClicked = {},
        favoritePokemons = PokemonSamples.listOfPokemons.subList(0,4)
    )
}