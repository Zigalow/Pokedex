package dtu.group21.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.liveLiteral
import androidx.compose.runtime.internal.updateLiveLiteralValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.pokedex.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dtu.group21.data.pokemon.StatPokemon
import dtu.group21.helpers.PokemonHelper.getGeneration
import dtu.group21.models.api.Resource
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.ui.favorites.FavoritePokemonBox
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize
import dtu.group21.ui.shared.buttonColor
import dtu.group21.ui.shared.mediumFontSize

val searchFieldColor = Color.hsv(0f, 0f, 0.95f)

@Composable
fun SearchBar(
    onChange: (String) -> Unit,
    height: Dp,
    modifier: Modifier = Modifier,
    backgroundColor: Color = searchFieldColor,
    showIcon: Boolean = true,
    centeredHorizontally: Boolean = true,
    initialText: String = "",
    placeholderText: String = "Search",
    textColor: Color = Color.Black,
    placeholderColor: Color = Color.Gray,
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
    ) {
        Row(
            modifier = modifier.height(height),
            horizontalArrangement = if (centeredHorizontally) Arrangement.Center else Arrangement.Start
        ) {
            // search icon
            if (showIcon) {
                Image(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search Icon",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(all = height / 10)
                        .size(height / 1.5f),
                )
            }
            var searchString by remember { mutableStateOf(initialText) }
            var isSearching by remember { mutableStateOf(initialText != "") }
            val textStyle = TextStyle(
                color = if (isSearching) textColor else placeholderColor,
                fontSize = TextUnit(
                    5f,
                    TextUnitType.Em
                ), // TODO: should respond to size of search box
                fontStyle = if (isSearching) FontStyle.Normal else FontStyle.Italic
            )
            // search field
            BasicTextField(
                value = searchString,
                onValueChange = {
                    searchString = it.replace("\n", "")
                    // search each time they change the contents of the search box
                    onChange(searchString)
                },
                textStyle = textStyle,
                singleLine = true,
                modifier = Modifier
                    .padding(all = height / 10)
                    .height(height)
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            if (!isSearching) {
                                searchString = ""
                            }
                            isSearching = true
                        } else {
                            isSearching = (searchString != "")
                            if (!isSearching) {
                                searchString = placeholderText
                            }
                        }
                    },
            )
        }
    }
}

@OptIn(InternalComposeApi::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToFilter: () -> Unit,
    onNavigateToSort: () -> Unit,
    onPokemonClicked: (String) -> Unit,
    searchSettings: SearchSettings,
    pokemonPool: MutableState<List<Resource<StatPokemon>>>,
    modifier: Modifier = Modifier,
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }
    val allCandidates = pokemonPool.value
    val candidates: State<List<Resource<StatPokemon>>> =
        liveLiteral("searchResults", allCandidates)

    updateCandidates(searchSettings, allCandidates)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UpperMenu(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
        ) {
            Spacer(Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back Arrow",
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onNavigateBack() },
                alignment = Alignment.CenterStart,
            )
            Text(
                text = "Search",
                modifier = Modifier
                    .weight(0.01f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = bigFontSize,
            )
            Spacer(Modifier.width(45.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        SearchBar(
            onChange = {
                println("Searched for '$it'")
                searchSettings.searchString = it
                updateCandidates(searchSettings, allCandidates)
            },
            height = 40.dp,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth(0.9f),
            initialText = searchSettings.searchString,
        )
        Spacer(Modifier.height(10.dp))
        Row {
            val buttonPadding = 15.dp
            val buttonWidth = 120.dp
            val buttonColors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = Color.Black
            )

            Button(
                onClick = { onNavigateToFilter() },
                modifier = Modifier
                    .padding(horizontal = buttonPadding)
                    .defaultMinSize(minWidth = buttonWidth),
                colors = buttonColors,
            ) {
                Text(
                    text = "Filter",
                    fontSize = mediumFontSize,
                    fontWeight = if (searchSettings.filterSettings.hasFilterTypeSettings()) FontWeight.Black else FontWeight.Normal,
                )
            }

            Button(
                onClick = { onNavigateToSort() },
                modifier = Modifier
                    .padding(horizontal = buttonPadding)
                    .defaultMinSize(minWidth = buttonWidth),
                colors = buttonColors,
            ) {
                Text(
                    text = "Sort",
                    fontSize = mediumFontSize,
                    fontWeight = if (searchSettings.sortSettings.hasSettings()) FontWeight.Black else FontWeight.Normal,
                )
            }
        }

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            if (candidates.value.isEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text("No Pokémon matching criteria")
            } else {
                candidates.value.forEach { pokemonResource ->
                    FavoritePokemonBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        pokemonResource = pokemonResource,
                        onClicked = onPokemonClicked
                    )
                }
            }
        }
    }
}

@OptIn(InternalComposeApi::class)
fun updateCandidates(
    searchSettings: SearchSettings,
    allCandidates: List<Resource<StatPokemon>>
) {
    val loadedCandidates =
        allCandidates.filter { it is Resource.Success }.map { (it as Resource.Success).data }
    val unloadedCandidates = Array(allCandidates.size - loadedCandidates.size) { Resource.Loading }

    val nameCandidates = loadedCandidates.filter { pokemon ->
        // very complicated statement to check if the searchString is either
        // - empty
        // - a substring of the name of the pokemon
        // - a substring of the number of the pokemon
        // if either is true, it is a candidate
        val candidate =
            if (searchSettings.searchString.isEmpty())
                true
            else if (searchSettings.searchString.isDigitsOnly()) {
                val searchNumber = searchSettings.searchString.toInt()
                searchNumber.toString() in pokemon.pokedexId.toString()
            } else searchSettings.searchString.lowercase() in pokemon.name.lowercase()

        candidate
    }

    val typeCandidates = nameCandidates.filter { pokemon ->
        val candidate =
            if (!searchSettings.filterSettings.hasFilterTypeSettings()) {
                true
            } else if (searchSettings.filterSettings.filterType == FilterSettings.FilterType.IncludableTypes) {
                if (searchSettings.filterSettings.types[pokemon.primaryType.ordinal]) true
                else searchSettings.filterSettings.types[pokemon.secondaryType.ordinal]
            } else if (searchSettings.filterSettings.filterType == FilterSettings.FilterType.ExactTypes) {
                if (searchSettings.filterSettings.numberOfTypesChosen() == 1) {
                    searchSettings.filterSettings.types[pokemon.primaryType.ordinal] && pokemon.secondaryType == PokemonType.NONE
                } else if (searchSettings.filterSettings.numberOfTypesChosen() == 2) {
                    searchSettings.filterSettings.types[pokemon.primaryType.ordinal] && searchSettings.filterSettings.types[pokemon.secondaryType.ordinal]
                } else {
                    false
                }
            } else false

        candidate
    }

    val generationCandidates = typeCandidates.filter { pokemon ->
        val candidate =
            if (!searchSettings.filterSettings.hasFilterGenerationsSettings()) {
                true
            } else searchSettings.filterSettings.generations[getGeneration(pokemon.pokedexId) - 1]

        candidate
    }

    val sortedStatCandidates = generationCandidates.sortedBy { pokemon ->
        val candidate =
            when (searchSettings.sortSettings.sortMethod) {
                SortSettings.SortMethod.ID -> pokemon.pokedexId
                SortSettings.SortMethod.HP -> pokemon.hp
                SortSettings.SortMethod.ATTACK -> pokemon.attack
                SortSettings.SortMethod.DEFENSE -> pokemon.defense
                SortSettings.SortMethod.SPECIAL_ATTACK -> pokemon.specialAttack
                SortSettings.SortMethod.SPECIAL_DEFENSE -> pokemon.specialDefense
                SortSettings.SortMethod.SPEED -> pokemon.speed
                SortSettings.SortMethod.TOTAL -> pokemon.total
                else -> pokemon.pokedexId
            }
        candidate
    }

    val sortedNameCandidates = generationCandidates.sortedBy { pokemon ->
        val candidate = pokemon.name
        candidate
    }

    val orderedCandidates =

        if (searchSettings.sortSettings.sortType == SortSettings.SortType.Descending) {
            if (searchSettings.sortSettings.sortMethod == SortSettings.SortMethod.NAME) {
                sortedNameCandidates.reversed()
            } else sortedStatCandidates.reversed()
        } else {
            if (searchSettings.sortSettings.sortMethod == SortSettings.SortMethod.NAME) {
                sortedNameCandidates
            } else sortedStatCandidates
        }

// TODO: decide whether unloaded pokemons should also be shown here
    val results = orderedCandidates.map { Resource.Success(it) } + unloadedCandidates

    updateLiveLiteralValue(
        "searchResults",
        results
    )
}
