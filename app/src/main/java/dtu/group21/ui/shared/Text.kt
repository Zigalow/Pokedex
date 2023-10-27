package dtu.group21.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

val smallFontSize  = TextUnit(15f, TextUnitType.Sp)
val mediumFontSize = TextUnit(18f, TextUnitType.Sp)
val bigFontSize    = TextUnit(35f, TextUnitType.Sp)

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    spaceBelow: Dp = 15.dp,
    spaceAbove: Dp = 5.dp,
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(Modifier.height(spaceAbove))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = bigFontSize,
        )
        Spacer(Modifier.height(spaceBelow))
    }
}