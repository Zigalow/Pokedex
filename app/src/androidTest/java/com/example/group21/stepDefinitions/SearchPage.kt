import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.room.util.newStringBuilder
import dtu.group21.models.api.PokemonViewModel
import dtu.group21.models.pokemon.ComplexPokemon
import dtu.group21.ui.PokeNavHost
import io.cucumber.java.en.Given


class SearchPage {

    val composeTestRule = createComposeRule()
    
    @Given("^the user is on the search page$")
    fun theUserIsOnTheSearchPage() {
        composeTestRule.setContent {
            PokeNavHost("search")
        }
    }
}