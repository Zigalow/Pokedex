package dtu.group21.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.search.SearchSettings
import dtu.group21.ui.shared.Title
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
                fontSize = TextUnit(5f, TextUnitType.Em), // TODO: should respond to size of search box
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

@Composable
fun SearchScreen(
    onNavigateToFilter: () -> Unit,
    onNavigateToSort: () -> Unit,
    searchSettings: SearchSettings,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Title(text = "Search")
        SearchBar(
            onChange = { println("Searched for '$it'"); searchSettings.searchString = it },
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
            val buttonColors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = Color.Black)

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
                    fontWeight = if (searchSettings.filterSettings.hasSettings()) FontWeight.Black else FontWeight.Normal,
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
    }
}