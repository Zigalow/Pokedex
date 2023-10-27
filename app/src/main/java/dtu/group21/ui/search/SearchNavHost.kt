package dtu.group21.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Step1: get nav controller

// Step2: call NavHost

// Step3: add screens to nav graph

// Step4: define destinations

// Step5: add navigation actions

// Step6: add navigation arguments

@Composable
fun SearchNavHost(startDestination: String = "search") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("search") {
            SearchScreen(
                onNavigateToFilter = { navController.navigate("filter") },
                onNavigateToSort = { navController.navigate("sort") },
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable("filter") {
            FilterScreen(
                onDoneFiltering = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable("sort") {
            SortScreen(
                onDoneSorting = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}