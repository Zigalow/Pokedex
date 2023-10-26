package dtu.group21.pokedex

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.group21.ui.theme.PokedexTheme
import com.example.pokedex.R
import dtu.group21.models.pokemon.Pokemon
import dtu.group21.models.pokemon.PokemonType

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                PokedexTheme {
                    // A surface container us3ing the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Greeting("Android")
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SettingsPage() {
        var searchText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            Text(
                text = "Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)


            )

            Button(
                onClick = {/* Do something */ },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Yellow, Color.Black)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.night_mode),
                    contentDescription = null
                )
                Text(
                    text = "Dark mode",
                    modifier = Modifier.padding(start = 1.dp),
                    textAlign = TextAlign.Start


                )


            }
            Button(
                onClick = {/* Do something */ },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Yellow, Color.Black)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.guide),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .padding(all = 2.dp),
                )
                Text(
                    text = "Guide",
                    color = Color.Black
                )
            }
            Button(
                onClick = {/* Do something */ },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .align(Alignment.Start),
                colors = ButtonDefaults.buttonColors(Color.Yellow, Color.Black)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.about_us), contentDescription = null
                )

                Text(
                    text = "About us",
                    color = Color.Black,
                    textAlign = TextAlign.Start

                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun SettingspagePreview() {
        SettingsPage()
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PokedexTheme {
            Greeting("Android")
        }
    }
