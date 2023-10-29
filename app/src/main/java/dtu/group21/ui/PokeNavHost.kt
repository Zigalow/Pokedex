package dtu.group21.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dtu.group21.ui.frontpage.FrontPage
import dtu.group21.ui.search.FilterScreen
import dtu.group21.ui.search.SearchScreen
import dtu.group21.ui.search.SearchSettings
import dtu.group21.ui.search.SortScreen

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

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            FrontPage(
                onNavigate = { navController.navigate(it) }
            )
        }
        composable("search") {
            SearchScreen(
                onNavigateToFilter = { navController.navigate("filter") },
                onNavigateToSort = { navController.navigate("sort") },
                searchSettings = searchSettings,
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable("filter") {
            FilterScreen(
                onDoneFiltering = { navController.popBackStack() },
                filterSettings = searchSettings.filterSettings,
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable("sort") {
            SortScreen(
                onDoneSorting = { navController.popBackStack() },
                sortSettings = searchSettings.sortSettings,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}