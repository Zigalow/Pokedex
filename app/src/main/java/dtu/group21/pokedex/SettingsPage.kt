package dtu.group21.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage() {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        Text(
            text = "Settings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp)
        )

        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)


        )

        Button(
            onClick = {/* Do something */ },
            modifier = Modifier
                .padding(top = 8.dp),
//                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Yellow, Color.Black)

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
//                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Yellow, Color.Black)
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
//                .fillMaxWidth()
//                .align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(Color.Yellow, Color.Black)

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
}