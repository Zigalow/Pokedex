package dtu.group21.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dtu.group21.models.database.AppDatabase
import dtu.group21.models.database.DatabaseViewModel
import dtu.group21.models.database.PokemonData
import dtu.group21.ui.PokeNavHost

import dtu.group21.ui.theme.PokedexTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "pokedex-database").build()

        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(Color.White)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White//MaterialTheme.colorScheme.background
                ) {
                    PokeNavHost()
                }
            }
        }
    }

    companion object {
        var database: AppDatabase? = null
    }
}

