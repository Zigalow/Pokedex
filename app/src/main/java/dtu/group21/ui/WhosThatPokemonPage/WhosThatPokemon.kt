package dtu.group21.ui.WhosThatPokemonPage

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import dtu.group21.ui.search.SearchBar
import dtu.group21.ui.shared.UpperMenu
import dtu.group21.ui.shared.bigFontSize
import dtu.group21.ui.shared.buttonColor

class WhosThatPokemon {

/*
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
                    text = "Settings",
                    modifier = Modifier.weight(0.01f).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = bigFontSize,
                )
                Spacer(Modifier.width(45.dp))
            }
            Spacer(Modifier.height(5.dp))
            SearchBar(
                onChange = { /*TODO*/ },
                height = 40.dp
            )

            /*
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
             */

            Button(
                onClick = {/* Do something */ },
                modifier = Modifier
                    .padding(top = 8.dp),
//                .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(buttonColor, Color.Black)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.night_mode),
                    contentDescription = null
                )
                Text(
                    text = "Dark mode",
                    modifier = Modifier
                        .padding(start = 1.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start

                )


            }
            Button(
                onClick = {/* Do something */ },
                modifier = Modifier
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(buttonColor, Color.Black)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.guide),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .padding(all = 2.dp),
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Guide",
                    color = Color.Black
                )
            }
            Button(
                onClick = {/* Do something */ },
                modifier = Modifier
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(buttonColor, Color.Black)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.about_us), contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "About us",
                    color = Color.Black,
                    textAlign = TextAlign.Start

                )
            }
        }
        */
}