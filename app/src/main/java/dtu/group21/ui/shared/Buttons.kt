package dtu.group21.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dtu.group21.ui.search.FilterSettings

val buttonColor = Color(0xFFFFCC00)
val unselectedToggleColor = Color.hsv(0f, 0f, 0.85f)

@Composable
fun RoundedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    foregroundColor: Color = Color.Black
) {
    RoundedSquare(size = 8.dp) {
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = foregroundColor,
            ),
        ) {
            Text(
                text = text,
                fontSize = mediumFontSize,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(filterSettings: FilterSettings, options: List<String>, indexOfLastItem: Int) {
    val selectedItem = remember {
        mutableStateOf(filterSettings.filterOption.name)
    }

    val expanded = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    )


    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value },
        ) {

        TextField(

            value = selectedItem.value,
            onValueChange = {},
            readOnly = true,
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
//            },
            modifier = Modifier
                .menuAnchor(),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = mediumFontSize
            )

        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
//            modifier = Modifier.background(Color.Gray)
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedItem.value = item
                        filterSettings.filterOption = FilterSettings.FilterOption.valueOf(item)
                        expanded.value = false
                    })
            }
        }
    }


}


@Composable
fun ToggleButton(
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isClickedInitially: Boolean = false,
    offBackgroundColor: Color = Color.White,
    offForegroundColor: Color = Color.Black,
    onBackgroundColor: Color = offForegroundColor,
    onForegroundColor: Color = offBackgroundColor,
    content: @Composable () -> Unit = {},
) {
    var isOn by remember { mutableStateOf(isClickedInitially) }
    val foreground = if (isOn) onForegroundColor else offForegroundColor
    val background = if (isOn) onBackgroundColor else offBackgroundColor

    Color.Yellow
    Button(
        onClick = { isOn = !isOn; onClick(isOn) },
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = foreground,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

@Composable
fun BinaryChooser(
    option1: String,
    option2: String,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    startsAt: Int = 0,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = Color.LightGray,
    primaryColor: Color = Color.Black,
    secondaryColor: Color = Color.White
) {
    Row(
        modifier = modifier.fillMaxWidth(1f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var checked by remember { mutableStateOf(startsAt == 1) }
        Text(
            text = option1,
            modifier = Modifier.weight(0.45f, true),
            color = if (checked) unselectedColor else selectedColor,
            textAlign = TextAlign.Center,
            fontSize = mediumFontSize,
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onChange(if (checked) 1 else 0)
            },
            modifier = Modifier.weight(0.1f, true),
            colors = SwitchDefaults.colors(
                checkedThumbColor = primaryColor,
                uncheckedThumbColor = primaryColor,
                checkedTrackColor = secondaryColor,
                uncheckedTrackColor = secondaryColor,
                checkedBorderColor = primaryColor,
                uncheckedBorderColor = primaryColor,
                checkedIconColor = secondaryColor,
                uncheckedIconColor = secondaryColor,
            ),
            thumbContent = {
                val icon =
                    if (checked) Icons.Filled.KeyboardArrowRight else Icons.Filled.KeyboardArrowLeft
                Icon(imageVector = icon, contentDescription = null)
            }
        )
        Text(
            text = option2,
            modifier = Modifier.weight(0.45f, true),
            color = if (checked) selectedColor else unselectedColor,
            textAlign = TextAlign.Center,
            fontSize = mediumFontSize,
        )
    }
}

@Composable
fun RadioOptions(
    options: Array<String>,
    onSelectionChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedInitially: Int = 0,
    selectedColor: Color = Color(0xFF167EFB),
    unselectedColor: Color = Color.Gray,
    textColor: Color = Color.Black,
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[selectedInitially]) }

    Column(
        modifier = modifier,
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = {
                            onOptionSelected(option)
                            onSelectionChanged(options.indexOf(option))
                        },
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = {
                        onOptionSelected(option)
                        onSelectionChanged(options.indexOf(option))
                    },
                    modifier = Modifier
                        .size(35.dp)
                        .scale(1.5f),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = selectedColor,
                        unselectedColor = unselectedColor,
                    )
                )
                Text(
                    text = option,
                    color = textColor,
                    fontSize = mediumFontSize,
                )
            }
        }
    }
}
