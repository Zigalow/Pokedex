package dtu.group21.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import dtu.group21.ui.shared.RadioOptions
import dtu.group21.ui.shared.Title
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize
import dtu.group21.ui.shared.buttonColor

@Composable
fun SortScreen(
    onNavigateBack: () -> Unit,
    onDoneSorting: () -> Unit,
    sortSettings: SortSettings,
    modifier: Modifier = Modifier,
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
                text = "Sort",
                modifier = Modifier.weight(0.01f).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = bigFontSize,
            )
            Spacer(Modifier.width(45.dp))
        }
        val options = remember { arrayOf(SortSettings.SortType.Ascending, SortSettings.SortType.Descending) }
        BinaryChooser(
            option1 = "Ascending",
            option2 = "Descending",
            onChange = { sortSettings.sortType = options[it] },
            startsAt = options.indexOf(sortSettings.sortType),
        )
        val sortOptions = remember { arrayOf("ID", "Name", "Total", "HP", "Attack", "Defense", "Special Attack", "Special Defense", "Speed") }
        RadioOptions(
            options = sortOptions,
            onSelectionChanged = { sortSettings.sortMethod = it },
            modifier = Modifier.padding(horizontal = 5.dp),
            selectedInitially = sortSettings.sortMethod,
        )

        Button(
            onClick = { onDoneSorting() },
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