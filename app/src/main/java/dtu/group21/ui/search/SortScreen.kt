package dtu.group21.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dtu.group21.ui.shared.BinaryChooser
import dtu.group21.ui.shared.RadioOptions
import dtu.group21.ui.shared.Title
import dtu.group21.ui.shared.bigFontSize
import dtu.group21.ui.shared.buttonColor

@Composable
fun SortScreen(
    onDoneSorting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Title(text = "Sort")
        Divider()
        BinaryChooser(
            option1 = "Ascending",
            option2 = "Descending",
            onChange = { /*TODO*/ },
        )
        val sortOptions = remember { arrayOf("ID", "Name", "Total", "HP", "Attack", "Defense", "Special Attack", "Special Defense", "Speed") }
        RadioOptions(
            options = sortOptions,
            onSelectionChanged = { /*TODO*/ },
            modifier = Modifier.padding(horizontal = 5.dp),
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