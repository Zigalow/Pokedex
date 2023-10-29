package dtu.group21.ui.favoritespage

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dtu.group21.ui.frontpage.PokemonColumn
import dtu.group21.models.pokemon.PokemonSamples.listOfPokemons

@Composable
fun favoritesPage() {
    PokemonColumn(pokemons = listOfPokemons, onPokemonClicked = {})

}


@Preview
@Composable
fun favortiesPreview() {
    favoritesPage()
}