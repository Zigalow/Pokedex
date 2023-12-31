package dtu.group21.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dtu.group21.models.api.PokemonViewModel
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.ui.favorites.FavoritesPage
import dtu.group21.ui.frontpage.FrontPage
import dtu.group21.ui.pokemonView.SpecificPage
import dtu.group21.ui.search.FilterScreen
import dtu.group21.ui.search.SearchScreen
import dtu.group21.ui.search.SearchSettings
import dtu.group21.ui.search.SortScreen
import dtu.group21.ui.settings.SettingsPage

// Step1: get nav controller

// Step2: call NavHost

// Step3: add screens to nav graph

// Step4: define destinations

// Step5: add navigation actions

// Step6: add navigation arguments

@Composable
fun PokeNavHost(startDestination: String = "home") {
    val navController = rememberNavController()
    val searchSettings = remember { SearchSettings() }
    val viewModel = remember {
        PokemonViewModel()
    }
    val pokemons = remember {
        mutableListOf<MutableState<ComplexPokemon>>()
    }
//    val ids = intArrayOf(6, 32, 35, 82, 133, 150, 668, 669).toTypedArray()
    val ids = IntRange(1, 20).toList().toTypedArray()
    LaunchedEffect(Unit) {
        viewModel.getPokemons(ids, pokemons)
    }


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            FrontPage(
                onNavigate = {
                    navController.navigate(it)
                }, pokemons
            )
        }
        composable("search") {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToFilter = { navController.navigate("filter") },
                onNavigateToSort = { navController.navigate("sort") },
                onPokemonClicked = { navController.navigate("pokemon/$it") },
                searchSettings = searchSettings,
                pokemonPool = pokemons,
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable("filter") {
            FilterScreen(
                onNavigateBack = { navController.popBackStack() },
                onDoneFiltering = { navController.popBackStack() },
                filterSettings = searchSettings.filterSettings,
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable("sort") {
            SortScreen(
                onNavigateBack = { navController.popBackStack() },
                onDoneSorting = { navController.popBackStack() },
                sortSettings = searchSettings.sortSettings,
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
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable("settings") {
            SettingsPage(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("favorites") {
            FavoritesPage(
                onNavigateBack = { navController.popBackStack() },
                onPokemonClicked = { navController.navigate("pokemon/$it") },
                //favoritePokemons = PokemonSamples.listOfPokemons.subList(2, 7)
            )
        }
    }
}
