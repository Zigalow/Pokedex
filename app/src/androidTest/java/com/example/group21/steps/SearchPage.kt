package com.example.group21.steps

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dtu.group21.ui.PokeNavHost
import io.cucumber.java.en.Given

class SearchPage {
    val composeTestRule = createComposeRule()

    @Given("the search screen has loaded")
    fun theUserIsOnTheSearchPage() {
        composeTestRule.setContent {
            PokeNavHost("search")
        }

        val searchBar = composeTestRule.onNodeWithText("Search", ignoreCase = true)
        println(searchBar)
    }
}