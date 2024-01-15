package dtu.group21.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dtu.group21.data.PokedexViewModel
import dtu.group21.data.pokemon.StatPokemon
import dtu.group21.models.api.Resource
import dtu.group21.ui.favorites.FavoritesPage
import dtu.group21.ui.frontpage.FrontPage
import dtu.group21.ui.pokemonView.SpecificPage
import dtu.group21.ui.pokemonView.isOnline
import dtu.group21.ui.search.FilterScreen
import dtu.group21.ui.search.SearchScreen
import dtu.group21.ui.search.SortScreen
import dtu.group21.ui.settings.SettingsPage

// Step1: get nav controller

// Step2: call NavHost

// Step3: add screens to nav graph

// Step4: define destinations

// Step5: add navigation actions

// Step6: add navigation arguments

/**
 *  Prevents rendering issues when clicking too quickly on back button. Functions the same way navController.popBackStack()
 */
fun popBackStackCustom(navController: NavHostController): Boolean {

    return if (navController.previousBackStackEntry == null) {
        false;
    } else
        navController.popBackStack();
}

@Composable
fun PokeNavHost(startDestination: String = "home") {
    var isOnline by remember { mutableStateOf(true) }
    isOnline = isOnline(LocalContext.current)
    if(isOnline){
        val navController = rememberNavController()

        val favouritePokemons = remember { mutableStateOf(emptyList<Resource<StatPokemon>>()) }
        LaunchedEffect(Unit) {
            val pokedexViewModel = PokedexViewModel()
            pokedexViewModel.getFavoritePokemons(favouritePokemons)
        }

        val pokemons = remember { mutableStateOf(emptyList<Resource<StatPokemon>>()) }
        LaunchedEffect(Unit) {
            val pokedexViewModel = PokedexViewModel()
            val ids = (1..151)
            pokedexViewModel.getPokemons(ids.toList(), pokemons)
        }

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable("home") {
                FrontPage(
                    onNavigate = {
                        navController.navigate(it)
                    },
                    pokemons = pokemons
                )
            }
            composable("search") {
                SearchScreen(
                    onNavigateBack = { popBackStackCustom(navController) },
                    onNavigateToFilter = { navController.navigate("filter") },
                    onNavigateToSort = { navController.navigate("sort") },
                    onPokemonClicked = { navController.navigate("pokemon/$it") },
                    pokemonPool = pokemons,
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable("searchFavourites") {
                SearchScreen(
                    onNavigateBack = { navController.popBackStack() },

                    //Changed to favourite filter and sort, so it filters and sorts accordingly to the favourite page.
                    onNavigateToFilter = { navController.navigate("filter") },
                    onNavigateToSort = { navController.navigate("sort") },

                    onPokemonClicked = { navController.navigate("pokemon/$it") },
                    pokemonPool = favouritePokemons,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable("filter") {
                FilterScreen(
                    onNavigateBack = { popBackStackCustom(navController) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable("sort") {
                SortScreen(
                    onNavigateBack = { popBackStackCustom(navController) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable(
                "pokemon/{pokedexId}",
                arguments = listOf(navArgument("pokedexId") { type = NavType.IntType })
            ) {
                it.arguments?.getInt("pokedexId")?.let { it1 ->
                    SpecificPage(
                        it1,
                        onNavigateBack = { popBackStackCustom(navController) }
                    )
                }
            }
            composable("settings") {
                SettingsPage(
                    onNavigateBack = { popBackStackCustom(navController) }
                )
            }
            composable("favorites") {
                FavoritesPage(
                    onNavigate = {
                        navController.navigate(it)
                    },
                    onNavigateBack = { navController.popBackStack() },
                    onPokemonClicked = { navController.navigate("pokemon/$it") },
                    //favoritePokemons = PokemonSamples.listOfPokemons.subList(2, 7)
                )
            }
        }

    }
    else {
        Column {
            Text(text = "No internet connection")
            Button(modifier = Modifier.size(200.dp),onClick = {
                isOnline = true
            }) {
                Icon(modifier = Modifier.fillMaxSize(),imageVector = Icons.Default.Refresh, contentDescription = null)
                Text("Refresh", modifier = Modifier.fillMaxSize())
            }

        }
    }
}