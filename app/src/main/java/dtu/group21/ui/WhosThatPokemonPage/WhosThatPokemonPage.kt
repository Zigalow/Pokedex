package dtu.group21.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import dtu.group21.ui.search.SearchBar
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize
import dtu.group21.ui.shared.buttonColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhosThatPokemonPage(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
                text = "Who's That Pokemon",
                modifier = Modifier.weight(0.01f).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = bigFontSize,
            )
            Spacer(Modifier.width(45.dp))
        }

    }
}