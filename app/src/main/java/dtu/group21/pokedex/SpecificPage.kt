package dtu.group21.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import dtu.group21.models.pokemon.Pokemon
import dtu.group21.models.pokemon.PokemonSamples
import dtu.group21.models.pokemon.PokemonType
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.frontpage.PokemonTypeBox

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SpecificPage() {
    //Mid(modifier = Modifier, PokemonSamples.bulbasaur)
    val pokemon =  PokemonSamples.charizard
    Inspect(pokemon = pokemon)
}

@Composable
fun Inspect(pokemon: Pokemon) {
    val modifier = Modifier
    Column(
        modifier
            .background(color = pokemon.type.secondaryColor)
            .fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            Top(pokemon = pokemon)

            Mid(modifier, pokemon)
        }
        Column(
            modifier.padding(start = 115.dp),
            verticalArrangement = Arrangement.Center
        ) {
            PokemonImage(pokemon = pokemon)
        }

        //Mid()
        Column(verticalArrangement = Arrangement.Bottom) {
            Bottom(pokemon  = pokemon)
        }
    }

}

@Composable
fun Top(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Row(
    ) {
        backIcon(
            modifier
                .padding(vertical = 16.dp, horizontal = 19.dp)
                .size(49.dp))
        Spacer(modifier.width(230.dp))
        FavoritesIcon(color = pokemon.type.secondaryColor) {
        }
        /*favoritesIconF(
            modifier = modifier
                .size(60.dp)
                .offset(0.dp, 11.dp)
                .background(
                    color = pokemonType.secondaryColor,
                    shape = CircleShape
                )
        )*/
        Spacer(modifier.width(11.dp))
    }
}

@Composable
fun Mid(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Column(
        modifier
            .height(105.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier
                .height(35.dp)
        ) {
            Spacer(modifier.width(13.dp))
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                text = pokemon.name,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                color = androidx.compose.ui.graphics.Color.White,
                fontSize = 30.sp
            )
            Text(
                text = "#" + pokemon.pokedexNumber,
                fontSize = 30.sp
            )
        }
        Spacer(modifier.height(24.dp))
        Row(
            modifier
                .height(35.dp)
        ) {
            Spacer(modifier.width(13.dp))
            PokemonTypeBox(
                modifier
                    .width(50.dp)
                    .height(18.dp)
                    .background(
                        color = pokemon.type.primaryColor,
                        shape = RoundedCornerShape(15.dp)
                    ), pokemonType = pokemon.type
            )
            Spacer(modifier.width(15.dp))
            PokemonTypeBox(
                modifier
                    .width(50.dp)
                    .height(18.dp)
                    .background(
                        color = pokemon.secondaryType.secondaryColor,
                        shape = RoundedCornerShape(15.dp)
                    ), pokemonType = pokemon.secondaryType
            )
        }
    }
}

@Composable
fun Bottom(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Column(
        modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(
                androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ),
        verticalArrangement = Arrangement.Bottom
    ) {
        val categories = listOf("About", "Stats", "Moves", "Evolution")
        var mySelectedCategory by remember { mutableStateOf<String?>(null) }
        Spacer(
            modifier
                .width(13.dp)
                .height(25.dp)
        )
        CategoryList(categories = categories, modifier, onCategorySelected = { selectedCategory ->
            mySelectedCategory = selectedCategory
        })
        Spacer(modifier.height(13.dp))
        Column(
            modifier
                .padding(start = 13.dp)
        ) {
            //based on which category is the coresponding section function will be used
            mySelectedCategory?.let { category ->
                Sections(selectedCategory = category, pokemon = pokemon, modifier = modifier)
            }
            Spacer(modifier.height(150.dp))
        }
    }
}

@Composable
fun Category(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = title,
            color = if (isSelected) androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.Black.copy(
                alpha = 0.2f
            ),
            textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
        )
    }
}

@Composable
fun CategoryList(categories: List<String>, modifier: Modifier, onCategorySelected: (String) -> Unit) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Row(modifier.fillMaxWidth()) {
        LazyRow() {
            items(categories) { category ->
                Spacer(modifier.width(51.dp))
                val isSelected = selectedCategory == category
                Category(
                    title = category,
                    isSelected = isSelected,
                    onClick = {
                        selectedCategory = category
                    }
                )

            }
        }
    }
    selectedCategory?.let { category ->
        onCategorySelected(category)
    }
}


@Composable
fun Table(first: String, second: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.4f),
            text = first,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = second,
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun Sections(modifier: Modifier,selectedCategory: String, pokemon: Pokemon){
    if (selectedCategory == "About"){
        AboutSection(modifier)
    }else if (selectedCategory == "Stats"){
        StatsSection(modifier)
    }else if(selectedCategory == "Moves"){
        MovesSection()
    }else if(selectedCategory == "Evolution"){
        EvolutionSection(modifier,pokemon)
    }
}
@Composable
fun AboutSection(modifier: Modifier) {
    Column {
        Table(first = "Category", second = "Seed")
        Table(first = "Abilities", second = "Overgrow")
        Table(first = "Weight", second = "12.2 lbs")
        Table(first = "Height", second = "2'04")
        Table(first = "Gender", second = "")
    }
    Column{
        Text(text = "Breeding")
        Table(first = "87.5%", second = "12.5%")
        Table(first = "Egg cycles", second = "20(4,884-5.140 steps)")
    }
    Spacer(modifier.fillMaxHeight())
}
@Composable
fun StatsSection(modifier: Modifier){
    Column {
        Table(first = "HP", second = "45")
        Table(first = "Attack", second = "45")
        Table(first = "Defense", second = "45")
        Table(first = "Sp.Atk", second = "45")
        Table(first = "Sp.Def", second = "45")
        Table(first = "Speed", second = "45")
        Table(first = "Total", second = "45")
    }
    Spacer(modifier.fillMaxHeight())
}
@Composable
fun MovesSection(){
    Column {

    }
}
@Composable
fun EvolutionSection(modifier: Modifier, pokemon :Pokemon){
    Row (modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween){
        repeat(2){index ->
            Column {
                PokemonImage(pokemon = pokemon, modifier = Modifier.size(75.dp))
                Text(text = "NameOfEvo", textAlign = TextAlign.Center)
            }
                arrow(modifier.size(25.dp))
        }
        Column {
            PokemonImage(pokemon = pokemon, modifier = Modifier.size(75.dp))
            Text(text = "NameOfEvo", textAlign = TextAlign.Center)
        }
    }
    Spacer(modifier.fillMaxHeight())
}


//region main components

/*@Composable
fun favoritesIconF(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .offset(0.dp, 11.dp)
            .background(
                color = pokemonType.secondaryColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.white_heart),
            contentDescription = "White heart",
            modifier = Modifier.size(30.dp)
        )
    }
}*/

@Composable
fun FavoritesIcon(modifier: Modifier = Modifier, color: Color, onClicked: () -> Unit) {
    Box(
        modifier = modifier
            .size(60.dp)
            .offset(0.dp, 11.dp)
            .background(
                shape = CircleShape,
                color = color
            )
            .clickable { onClicked() }, contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.white_heart),
            contentDescription = "White heart",
            modifier = Modifier.size(30.dp)
        )
    }
}


@Composable
fun backIcon(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.back_arrow),
        contentDescription = "search-icon",
        modifier = modifier,
    )
}
@Composable
fun arrow(modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.front_arrow),
        contentDescription = "arrow",
        modifier = modifier
    )
}
