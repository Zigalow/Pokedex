package dtu.group21.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import dtu.group21.data.pokemon.DisplayPokemon
import dtu.group21.models.api.Resource
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.shared.UpperMenu
import kotlinx.coroutines.delay
import kotlin.random.Random

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

    var showPokemon by remember { mutableStateOf(false) }

    val currentPokemon = pokemonPool.value[index]

    LaunchedEffect(showPokemon) {
        if (showPokemon) {
            showPokemon = true //showcase pokemon true
            delay(2000) // Delay for 3 seconds
            showPokemon=false
            index = Random.nextInt(pokemonPool.value.size) // Change to next Pokémon
        }
    }

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
               // .fillMaxWidth()
                .fillMaxSize()
                //.height(74.dp)
        ) {
            Spacer(Modifier.width(10.dp))
            Box(modifier = Modifier.weight(0.1f).fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back Arrow",
                    modifier = Modifier
                        .size(35.dp)
                        //.align(Alignment.CenterVertically)
                        .clickable { onNavigateBack() },
                    alignment = Alignment.CenterStart,
                )
            }

            Box(modifier = Modifier.weight(0.5f).fillMaxWidth(), contentAlignment = Alignment.Center){
                Image(
                    painter = painterResource(id = R.drawable.whos_that_pokemon_logo4),
                    contentDescription = "Who's That Pokemon?",
                    modifier = Modifier.fillMaxSize()
                )
            }
           // Spacer(Modifier.width(45.dp))
        }

        if (currentPokemon is Resource.Success) {
            PokemonImage(
                pokemon = currentPokemon.data,
                silhoutteColor = if (showPokemon) null else Color.Black
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    start = 10.dp,
                    end = 10.dp
                ) // Adjust start and end padding to reduce box width
                .background(
                    color = Color(0xFFE0E0E0), // Replace with your desired color
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = guess,
                onValueChange = { guess = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding inside the box
                label = { Text("Your Guess") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    containerColor = Color.Transparent, // No background color
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    )
            )
        }
        // Input field for the guess

        // Button to submit the guess
        Button(
            onClick = {
                if (currentPokemon is Resource.Success) {
                    if (currentPokemon.data.name.equals(guess, ignoreCase = true)) {
                        showPokemon = true // Reveal Pokémon
                    }
                }
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFCC00) // Set the button color using the hexadecimal value
            )
        ) {
            Text("Guess", fontSize = 18.sp, color = Color.White) // Set the text color to white for better contrast
        }

    }
}

