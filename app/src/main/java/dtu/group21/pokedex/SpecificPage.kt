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
import androidx.compose.ui.draw.scale
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
import dtu.group21.ui.frontpage.PokemonImage
import dtu.group21.ui.frontpage.PokemonTypeBox

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SpecificPage(onNavigateBack: () -> Unit) {
    //Mid(modifier = Modifier, PokemonSamples.bulbasaur)
    val pokemon =  PokemonSamples.charizard
    Inspect(pokemon = pokemon, onNavigateBack = onNavigateBack)
}

@Composable
fun Inspect(pokemon: Pokemon, onNavigateBack: () -> Unit) {
    val modifier = Modifier
    Column(
        modifier
            .background(color = pokemon.type.secondaryColor)
            .fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            Top(pokemon = pokemon, onClickBack = onNavigateBack)

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
fun Top(
    pokemon: Pokemon,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        backIcon(
            modifier
                .padding(vertical = 16.dp, horizontal = 19.dp)
                .size(49.dp)
                .clickable { onClickBack() }
        )
        Spacer(modifier.width(230.dp))
        var favorited by remember { mutableStateOf(false) }
        FavoritesIcon(
            active = favorited,
            color = pokemon.type.secondaryColor,
            onClicked = { favorited = !favorited }
        )
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
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 30.sp
            )
            Text(
                text = "#" + pokemon.pokedexNumber,
                fontSize = 30.sp
            )
            Spacer(modifier.width(13.dp))
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
        var selectedCategory by remember { mutableStateOf("About") }
        Spacer(
            modifier
                .width(13.dp)
                .height(25.dp)
        )
        CategoryList(
            categories = categories,
            onCategorySelected = { selectedCategory = it },
            initiallyChosen = selectedCategory,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier.height(13.dp))
        Column(
            modifier
                .padding(start = 13.dp)
        ) {
            //based on which category is the coresponding section function will be used
            Sections(selectedCategory = selectedCategory, pokemon = pokemon, modifier = modifier)
            Spacer(modifier.height(150.dp))
        }
    }
}

@Composable
fun Category(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.Black else Color.LightGray,
            textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
        )
    }
}

@Composable
fun CategoryList(
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    initiallyChosen: String = ""
) {
    var selectedCategory by remember { mutableStateOf(initiallyChosen) }

    LazyRow(
        modifier = modifier,
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category
            Category(
                title = category,
                isSelected = isSelected,
                onClick = {
                    selectedCategory = category
                },
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
    onCategorySelected(selectedCategory)
}


@Composable
fun Table(first: String, second: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            color = Color.Black.copy(alpha = 0.4f),
            text = first,
            modifier = Modifier.weight(0.35f)
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
    when (selectedCategory) {
        "About" -> AboutSection(modifier)
        "Stats" -> StatsSection(modifier)
        "Moves" -> MovesSection()
        "Evolution" -> EvolutionSection(modifier = Modifier
            .padding(horizontal = 2.dp)
            .fillMaxWidth(), pokemon)
    }
}
@Composable
fun AboutSection(modifier: Modifier) {
    Column {
        Table(first = "Category", second = "Seed")
        Table(first = "Abilities", second = "Overgrow")
        Table(first = "Weight", second = "12.2 lbs")
        Table(first = "Height", second = "2'04")
        //Table(first = "Gender", second = "")
    }
    Column{
        Text(text = "Breeding")
        Table("Male", "87.5%")
        Table("Female", "12.5%")
        Table(first = "Egg cycles", second = "20 (4,884-5.140 steps)")
    }
    Spacer(modifier.fillMaxHeight())
}
@Composable
fun StatsSection(modifier: Modifier){
    Column {
        Table(first = "HP", second = "78")
        Table(first = "Attack", second = "84")
        Table(first = "Defense", second = "78")
        Table(first = "Sp.Atk", second = "109")
        Table(first = "Sp.Def", second = "85")
        Table(first = "Speed", second = "100")
        Divider(Modifier.width(150.dp))
        Spacer(Modifier.height(5.dp))
        Table(first = "Total", second = "534")
    }
    Spacer(modifier.fillMaxHeight())
}
@Composable
fun MovesSection(){
    Column {

    }
}
@Composable
fun EvolutionSection(modifier: Modifier, pokemon: Pokemon){
    Row (
        modifier = modifier
    ) {
        val evolutionChain = arrayOf(PokemonSamples.charmander, PokemonSamples.charmeleon, PokemonSamples.charizard)
        for ((index, evolution) in evolutionChain.withIndex()) {
            Column {
                PokemonImage(
                    pokemon = evolution,
                    modifier = Modifier
                        .size(75.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 10.dp)
                )
                Text(
                    text = evolution.name.replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center
                )
            }
            if (index < 2)
                arrow(modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically))
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
fun FavoritesIcon(
    active: Boolean,
    color: Color,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .offset(0.dp, 11.dp)
            .background(
                shape = CircleShape,
                color = color
            )
            .clickable { onClicked() },
        contentAlignment = Alignment.Center
    ) {
        val imageId = if (active) R.drawable.favorite_icon_active else R.drawable.favorite_icon_inactive
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "White heart",
            modifier = Modifier.size(50.dp)
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
