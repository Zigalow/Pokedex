package dtu.group21.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import dtu.group21.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Column {
                    UpperMenu()
                    // Line
                    Divider(thickness = 1.dp, color = Color.Black)
                    Spacer(modifier = Modifier.padding(3.dp))
                    pokemonColumn(listOfPokemon = PokemonSamples.listOfPokemons)
                }
                favoritesIcon(modifier = Modifier.offset(290.dp, 675.dp))
                menu()
            }
        }
    }
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Inspect(){
        val modifier = Modifier
        //val color = Color(PokemonType.GRASS.backgroundColorHexvalue)
        Column(
            modifier.background(color = Color(android.graphics.Color.parseColor(PokemonType.GRASS.backgroundColorHexvalue)))
        ) {

        }
        Column(verticalArrangement = Arrangement.Top){
            Top()

            Mid()
        }
        Column(
            modifier.padding(start = 115.dp),
            verticalArrangement = Arrangement.Center
                ) {
            pokemonImage(pokemon = PokemonSamples.bulbasaur)
        }

        //Mid()
        Column(verticalArrangement = Arrangement.Bottom) {
            Bottom()
        }

    }
    @Composable
    fun Top(modifier: Modifier = Modifier){
        Row(
        ) {
                backIcon()
                Spacer(modifier.width(230.dp))
                favoritesIcon(modifier)
                Spacer(modifier.width(11.dp))
            }
    }
    @Composable
    fun Mid(modifier: Modifier  = Modifier){
        Column(
            modifier
                .height(105.dp)
                .fillMaxWidth()
        ){
            Row (modifier
                .height(35.dp)
            ){
                Spacer(modifier.width(13.dp))
                Text(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight(),
                    text = "Bulbasuar",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 30.sp
                )
                Text(
                    text = "#001",
                    fontSize = 30.sp
                )
            }
            Spacer(modifier.height(24.dp))
            Row(modifier
                .height(35.dp)
            ) {
                Spacer(modifier.width(13.dp))
                pokemonTypeBox(pokemonType = PokemonType.GRASS)
                Spacer(modifier.width(15.dp))
                pokemonTypeBox(pokemonType = PokemonType.POISON)
            }
        }
    }
    @Composable
    fun Bottom(modifier: Modifier  = Modifier){
        Column(
            //contentAlignment = Alignment.TopStart,
            modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White, shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp)),
            verticalArrangement = Arrangement.Bottom
        ){
            val categories = listOf("About","Stats", "Moves", "Evolution")
            Spacer(
                modifier
                    .width(13.dp)
                    .height(25.dp))
            CategoryList(categories = categories, modifier)
            Spacer(modifier.height(13.dp))
            Column(
                modifier
                    .padding(start = 13.dp)) {
                AboutSection()
                Spacer(modifier.height(150.dp))
            }
            }
        }
    @Composable
    fun Category(title: String, isSelected: Boolean, onClick: () -> Unit){
        // remember by boolean
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
        ) {
            Text(
                text = title,
                color = if (isSelected) Color.Black else Color.Black.copy(alpha = 0.2f),
                textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
            )
        }
    }
    @Composable
    fun CategoryList(categories: List<String>, modifier: Modifier){
        var selectedCategory by remember { mutableStateOf<String?>(null) }

        Row (modifier.fillMaxWidth()){
            LazyRow(){
                items(categories){category ->
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
    }


    @Composable
    fun Table(first: String, second: String){
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                color = Color.Black.copy(alpha = 0.4f),
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
    fun AboutSection(){
        Column {
            Table(first = "Category", second = "Seed")
            Table(first = "Abilities", second = "Overgrow")
            Table(first = "Weight", second = "12.2 lbs")
            Table(first = "Height", second = "2'04")
            Table(first = "Gender", second = "")
        }
    }


    //region main components
    @Composable
    fun UpperMenu(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(83.dp)
        ) {
            Row {
                menuIcon()
                Spacer(
                    modifier = modifier.padding(horizontal = 14.dp)
                )
                pokemonLogo()
                Spacer(
                    modifier = modifier.padding(horizontal = 14.dp)
                )
                searchIcon()
            }

        }


    }

    @Composable
    fun menu(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .width(80.dp)
                .background(Color(android.graphics.Color.parseColor("#FFCC00")))
        ) {
            menuIcon()
            Image(
                painter = painterResource(id = R.drawable.settings_icon), // Replace with your image resource
                contentDescription = "settings-icon", // Set to null if the image is decorative
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 19.dp)
                    .size(49.dp)
                    .offset(y = 675.dp),

                )
        }

    }

    @Composable
    fun pokemonColumn(modifier: Modifier = Modifier, listOfPokemon: List<Pokemon>) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
        ) {
            for (i in listOfPokemon.indices step 2) {
                Row()
                {
                    if (i < listOfPokemon.size) {
                        pokemonBox(pokemon = listOfPokemon[i])
                        Spacer(modifier = modifier.padding(horizontal = 12.dp))
                    }
                    if (i + 1 < listOfPokemon.size) pokemonBox(pokemon = listOfPokemon[i + 1])
                }
                Spacer(modifier = modifier.padding(vertical = 5.dp))
            }
        }
    }

    @Composable
    fun favoritesIcon(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .size(60.dp)
                .offset(0.dp, 11.dp)
                .background(
                    Color(android.graphics.Color.parseColor("#DE4A4A")),
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
    }
    //endregion

    //helper functions to components
    
    //region upper menu component functions
    @Composable
    fun pokemonLogo(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.pokemon_logo),
            contentDescription = "Pokemon logo",
            modifier = Modifier
                .height(87.dp)
                .width(154.dp)
        )
    }

    @Composable
    fun searchIcon(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "search icon",
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 19.dp)
                .size(49.dp)

        )
    }

    @Composable
    fun menuIcon(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.menu_icon), // Replace with your image resource
            contentDescription = "menu-icon", // Set to null if the image is decorative
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 19.dp)
                .size(49.dp),
        )
    }
    @Composable
    fun backIcon(){
        Image(
            painter =painterResource(id = R.drawable.back_arrow),
            contentDescription = "search-icon",
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 19.dp)
                .size(49.dp)
        )
    }
    //endregion

    //region pokemon column functions
    @Composable
    fun pokemonTypeBox(modifier: Modifier = Modifier, pokemonType: PokemonType) {
        Box(
            modifier = modifier
                .width(50.dp)
                .height(18.dp)
                .background(
                    Color(android.graphics.Color.parseColor(pokemonType.regularColorHexvalue)),
                    shape = RoundedCornerShape(15.dp)
                ), contentAlignment = Alignment.Center

        ) {
            Text(
                text = pokemonType.name.toLowerCase().capitalize(),
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }

    @Composable
    fun pokemonImage(modifier: Modifier = Modifier, pokemon: Pokemon) {
        Image(
            painter = painterResource(id = pokemon.spriteResourceId),
            contentDescription = pokemon.name,
            modifier = Modifier
                .size(174.dp)
                .background(
                    Color(android.graphics.Color.parseColor(pokemon.type.backgroundColorHexvalue)),
                    shape = RoundedCornerShape(20.dp)
                )
        )
    }

    @Composable
    fun pokemonNameBox(modifier: Modifier = Modifier, pokemon: Pokemon) {
        Box(
            modifier = modifier
                .width(120.dp)
                .height(23.dp)
                .offset(x = 8.dp, y = 145.dp)
                .background(
                    Color(android.graphics.Color.parseColor(pokemon.type.regularColorHexvalue)),
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                text = pokemon.name.toLowerCase().capitalize(),
                fontSize = 17.sp,
                color = Color.White,
            )
        }
    }

    fun formatPokemonId(unformattedNumber: Int): String {
        return "#${"%04d".format(unformattedNumber)}"
    }

    @Composable
    fun pokemonBox(modifier: Modifier = Modifier, pokemon: Pokemon) {
        Box(
            modifier = modifier
                .size(174.dp)
//                .background(
//                    Color(android.graphics.Color.parseColor(pokemon.type.regularColorHexvalue)),
//                    shape = RoundedCornerShape(50.dp)
//                )
        ) {
            pokemonImage(pokemon = pokemon)
            Row(
                modifier = modifier
                    .padding(horizontal = 6.dp, 11.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                pokemonTypeBox(pokemonType = pokemon.type)
                if (pokemon.secondaryType != null) {
                    Spacer(modifier = modifier.padding(horizontal = 4.dp))
                    pokemonTypeBox(pokemonType = pokemon.secondaryType)
                }
                // For displaying pokedex number
                Box(
                    modifier = Modifier.weight(1f), // Takes up remaining space
                    contentAlignment = Alignment.CenterEnd // Align the content to the end (right)
                ) {
                    Text(
                        text = formatPokemonId(pokemon.pokedexNumber),
                        textAlign = TextAlign.Right,
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
            pokemonNameBox(pokemon = pokemon)
        }
    }

    //endregion


    //@Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {
        Column {
            UpperMenu()
            // Line
            Divider(thickness = 1.dp, color = Color.Black)
            Spacer(modifier = Modifier.padding(3.dp))
            pokemonColumn(listOfPokemon = PokemonSamples.listOfPokemons)
        }
        favoritesIcon(modifier = Modifier.offset(290.dp, 675.dp))
        //menu()
    }
}
    
