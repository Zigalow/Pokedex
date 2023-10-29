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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import dtu.group21.ui.shared.BinaryChooser
import dtu.group21.ui.shared.Title
import dtu.group21.ui.shared.ToggleButton
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize
import dtu.group21.ui.shared.buttonColor
import dtu.group21.ui.shared.mediumFontSize
import dtu.group21.ui.shared.unselectedToggleColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    onNavigateBack: () -> Unit,
    onDoneFiltering: () -> Unit,
    filterSettings: FilterSettings,
    modifier: Modifier = Modifier
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
                    .clickable { onNavigateBack() },
                alignment = Alignment.CenterStart,
            )
            Text(
                text = "Filter",
                modifier = Modifier.weight(0.01f).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = bigFontSize,
            )
            Spacer(Modifier.width(45.dp))
        }
        val options = remember { arrayOf(FilterSettings.FilterType.IncludableTypes, FilterSettings.FilterType.DualType) }
        BinaryChooser(
            option1 = "Includable Types",
            option2 = "Dual Type",
            onChange = { filterSettings.filterType = options[it] },
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
                val typeNames = remember { arrayOf("Normal", "Flying", "Fire", "Psychic", "Water", "Bug", "Grass", "Rock", "Electric", "Ghost", "Ice", "Dragon", "Fighting", "Dark", "Poison", "Steel", "Ground", "Fairy", "Tera") }
                val typeColors = remember { arrayOf(Color(0xFFA8A878), Color(0xFFA890F0), Color(0xFFEF7F2F), Color(0xFFF85888), Color(0xFF688FEF), Color(0xFFA8B820), Color(0xFF9BCC50), Color(0xFFB8A038), Color(0xFFF8D030), Color(0xFF705898), Color(0xFF98D8D8), Color(0xFF7038F8), Color(0xFFBF3028), Color(0xFF705848), Color(0xFFA040A0), Color(0xFFB8B8D0), Color(0xFFDFBF68), Color(0xFFF0B6BC), Color(0xFF68A090)) }

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    maxItemsInEachRow = 2,
                ) {
                    val buttonCount = typeNames.size
                    val hasExtraButton = (buttonCount % 2 == 1)
                    val columnsButtonCount = buttonCount - if (hasExtraButton) 1 else 0

                    for (i in 0 until columnsButtonCount) {
                        ToggleButton(
                            onClick = {
                                filterSettings.types[i] = it
                            },
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .weight(0.5f, true),
                            offBackgroundColor = unselectedToggleColor,
                            offForegroundColor = Color.Black,
                            onBackgroundColor = typeColors[i],
                            onForegroundColor = Color.White,
                            isClickedInitially = filterSettings.types[i],
                        ) {
                            Text(
                                text = typeNames[i],
                                fontSize = mediumFontSize,
                            )
                        }
                    }

                    // If there is an uneven amount of buttons
                    if (hasExtraButton) {
                        ToggleButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .fillMaxWidth(0.5f),
                            offBackgroundColor = unselectedToggleColor,
                            offForegroundColor = Color.Black,
                            onBackgroundColor = typeColors.last(),
                            onForegroundColor = Color.White,
                        ) {
                            Text(
                                text = typeNames.last(),
                                fontSize = mediumFontSize,
                            )
                        }
                    }
                }
            }
            Button(
                onClick = { onDoneFiltering() },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = Color.Black)
            ) {
                Text(
                    text = "Apply",
                    fontSize = bigFontSize,
                )
            }

        }
    }
}