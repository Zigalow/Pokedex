package dtu.group21.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import dtu.group21.data.database.AppDatabase
import dtu.group21.data.database.DatabaseViewModel
import dtu.group21.data.database.PokemonData
import dtu.group21.ui.PokeNavHost

import dtu.group21.ui.theme.PokedexTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "pokedex-database").build()

        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
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

