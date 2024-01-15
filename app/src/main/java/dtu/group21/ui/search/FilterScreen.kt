package dtu.group21.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import dtu.group21.data.pokemon.PokemonType
import dtu.group21.ui.search.SearchSettings.filterSettings
import dtu.group21.ui.shared.BinaryChooser
import dtu.group21.ui.shared.DropDownMenu
import dtu.group21.ui.shared.ToggleButton
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize
import dtu.group21.ui.shared.mediumFontSize
import dtu.group21.ui.shared.unselectedToggleColor

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = modifier,
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
                    .clickable {
                        onNavigateBack()
                    },
                alignment = Alignment.CenterStart,

                )
            Text(
                text = "Filter",
                modifier = Modifier
                    .weight(0.01f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = bigFontSize,
            )
            Spacer(Modifier.width(45.dp))
        }

        val currentOption = remember {
            mutableStateOf(filterSettings.filterOption)
        }
        
        FilterOptionsBox(onChange = { index, value ->
            currentOption.value = when (index) {
                0 -> FilterSettings.FilterOption.TYPES
                1 -> FilterSettings.FilterOption.GENERATIONS
                else -> FilterSettings.FilterOption.TYPES
            }
        })

        when (currentOption.value) {
            FilterSettings.FilterOption.TYPES -> TypeFilterScreen()
            FilterSettings.FilterOption.GENERATIONS -> GenerationFilterScreen()
        }
    }
}

@Composable
fun FilterOptionsBox(
    onChange: (Int, String) -> Unit
) {
    Spacer(modifier = Modifier.height(35.dp))
    val filterOptions = remember {
        FilterSettings.FilterOption.entries.map { item -> item.name.uppercase() }
    }

    DropDownMenu(
        options = filterOptions,
        onChange = onChange
    )

    Spacer(modifier = Modifier.height(30.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TypeFilterScreen() {
    val hasTooManyTypesSelected = remember {
        mutableStateOf(shouldShowWarning())
    }

    val options = remember {
        arrayOf(
            FilterSettings.FilterType.IncludableTypes,
            FilterSettings.FilterType.ExactTypes
        )
    }
    BinaryChooser(
        option1 = "Includable Types",
        option2 = "Exact Types",
        onChange = {
            filterSettings.filterType = options[it]
            hasTooManyTypesSelected.value = shouldShowWarning()
        },
        startsAt = options.indexOf(filterSettings.filterType),
    )
    Spacer(Modifier.height(5.dp))
    /*
    Text(
        text = "Types",
        textAlign = TextAlign.Center,
        fontSize = smallFontSize,
    )
    Spacer(Modifier.height(5.dp))
     */
    Column(
        modifier = Modifier.fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            // all type buttons are below
            val types = remember {
                arrayOf(
                    PokemonType.NORMAL,
                    PokemonType.FLYING,
                    PokemonType.FIRE,
                    PokemonType.PSYCHIC,
                    PokemonType.WATER,
                    PokemonType.BUG,
                    PokemonType.GRASS,
                    PokemonType.ROCK,
                    PokemonType.ELECTRIC,
                    PokemonType.GHOST,
                    PokemonType.ICE,
                    PokemonType.DRAGON,
                    PokemonType.FIGHTING,
                    PokemonType.DARK,
                    PokemonType.POISON,
                    PokemonType.STEEL,
                    PokemonType.GROUND,
                    PokemonType.FAIRY
                )
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center,
                maxItemsInEachRow = 2,
            ) {
                val buttonCount = types.size
                val hasExtraButton = (buttonCount % 2 == 1)
                val columnsButtonCount = buttonCount - if (hasExtraButton) 1 else 0

                for (i in 0 until columnsButtonCount) {
                    ToggleButton(
                        onClick = {
                            filterSettings.types[i] = it
                            hasTooManyTypesSelected.value = shouldShowWarning()
                        },
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .weight(0.5f, true),
                        offBackgroundColor = unselectedToggleColor,
                        offForegroundColor = Color.Black,
                        onBackgroundColor = types[i].primaryColor,
                        onForegroundColor = Color.White,
                        isClickedInitially = filterSettings.types[i],
                    ) {
                        Text(
                            text = types[i].name,
                            fontSize = mediumFontSize,
                        )
                    }
                }
            }
        }
        if (hasTooManyTypesSelected.value) {
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Warning: No Pokémons have more than two types"
            )
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenerationFilterScreen() {
    Column(
        modifier = Modifier.fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            // all generations buttons are below
            val generations = remember {
                intArrayOf(
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9
                )
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(0.9F)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center,
                maxItemsInEachRow = 1,
            ) {
                val buttonCount = generations.size
//                val hasExtraButton = (buttonCount % 2 == 1)
                val columnsButtonCount = buttonCount /*- if (hasExtraButton) 1 else 0*/

                for (i in 0 until columnsButtonCount) {
                    ToggleButton(
                        onClick = {
                            filterSettings.generations[i] = it
                        },
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .weight(0.5f, true),
                        offBackgroundColor = unselectedToggleColor,
                        offForegroundColor = Color.Black,
                        onBackgroundColor = Color(0xFFFFCC00),
                        onForegroundColor = Color.White,
                        isClickedInitially = filterSettings.generations[i],
                    ) {
                        Text(
                            text = generations[i].toString(),
                            fontSize = mediumFontSize,
                        )
                    }
                }
            }
        }
    }
}

fun shouldShowWarning(): Boolean {
    return filterSettings.numberOfTypesChosen() > 2 && filterSettings.filterType == FilterSettings.FilterType.ExactTypes
}
