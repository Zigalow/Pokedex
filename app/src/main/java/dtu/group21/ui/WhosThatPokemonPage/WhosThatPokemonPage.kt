package dtu.group21.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.models.api.Resource
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhosThatPokemonPage(
    onNavigateBack: () -> Unit,
    pokemonPool: MutableState<List<Resource<DisplayPokemon>>>,
) {
    var guess by remember { mutableStateOf("") }
    var index by remember {
        mutableStateOf(0)
    }
    val currentPokemon = pokemonPool.value[index]

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
                text = "Who's That Pokemon",
                modifier = Modifier
                    .weight(0.01f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = bigFontSize,
            )
            Spacer(Modifier.width(45.dp))
        }
        /* Display the Pokémon silhouette
        Image(
            //painter = painterResource(id = pokemonImage),
            contentDescription = "Pokemon Silhouette",
            modifier = Modifier
                .size(200.dp)
                .padding(top = 20.dp)
        )
        */
        if (currentPokemon is Resource.Success){
            PokemonImage(
                pokemon = currentPokemon.data,
                silhoutteColor = Color.Black
            )
        }
        // Input field for the guess
        TextField(
            value = guess,
            onValueChange = { guess = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            label = { Text("Your Guess") }
        )

        // Button to submit the guess

        Button(
            onClick = {
                if(currentPokemon is Resource.Success){
                    if(currentPokemon.data.name.equals(guess)) index ++
                }
                      },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text("Guess", fontSize = 18.sp)
        }
    }
}
/*

class WhosThatPokemonViewModel : ViewModel() {

    //Eksempler for billeder

    //var currentPokemonSilhouette: Int = R.drawable.pokemon_silhouette // Placeholder
    //private var currentPokemonName: String = "pikachu" // Placeholder

    fun checkGuess(guess: String) {
        if (guess.equals(currentPokemonName, ignoreCase = true)) {
                //If correct, input some text saying it's correct
        } else {
            //If wrong, input some text saying it's wrong.
        }
    }
}*/
//Mangler: to add logic to update currentPokemonSilhouette and currentPokemonName
