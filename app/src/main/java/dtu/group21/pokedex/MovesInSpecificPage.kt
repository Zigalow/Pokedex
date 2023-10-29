package dtu.group21.pokedex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dtu.group21.models.pokemon.BulbasaurMovesList
import dtu.group21.models.pokemon.PokemonMove


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoveBoxColumn(moveList: List<PokemonMove>) {
    val primaryWeight = 0.24f
    val secondaryWeight = 0.76f

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        maxItemsInEachRow = 2
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Level",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(secondaryWeight),
                text = "Move",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(primaryWeight),

                text = "Power",
                textAlign = TextAlign.Center
            )


            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "Acc.",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(primaryWeight),
                text = "PP",
                textAlign = TextAlign.Center
            )
        }
        Divider(color = Color.Black)

        for (i in moveList.indices) {
            moveBox(
//                modifier = modifier
//                    .size(190.dp)
//                    .padding(horizontal = 8.dp, vertical = 5.dp),
                move = moveList[i],
//                onClicked = { onPokemonClicked(pokemons[i].name) }
            )
            Divider(modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
        }
    }
}


@Composable
fun moveBox(move: PokemonMove) {
    val primaryWeightUpper = 0.24f
    val secondaryWeightUpper = 0.76f

    val primaryWeightBottom = 0.6f
    val secondaryWeightBottom = 0.4f

    Box {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.weight(primaryWeightUpper),
                    text = move.level.toString(),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.weight(secondaryWeightUpper),
                    text = move.name,
                    textAlign = TextAlign.Center
                )
                val power: String
                if (move.power == null) {
                    power = "-"
                } else {
                    power = move.power.toString()
                }
                Text(
                    modifier = Modifier.weight(primaryWeightUpper),

                    text = power,
                    textAlign = TextAlign.Center
                )

                val accuracy: String
                if (move.accuracy == null) {
                    accuracy = "-"
                } else {
                    accuracy = move.accuracy.toString()
                }
                Text(
                    modifier = Modifier.weight(primaryWeightUpper),
                    text = accuracy,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.weight(primaryWeightUpper),
                    text = move.pp.toString(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Row()
            {
                Box(
                    modifier = Modifier
                        .weight(secondaryWeightBottom)
                        .background(
                            shape = RoundedCornerShape(15.dp),
                            color = move.type.primaryColor
                        ), contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = move.type.name,
                        // todo
                        fontSize = 12.sp, color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(primaryWeightBottom)
                        .background(
                            shape = RoundedCornerShape(15.dp),
                            color = move.moveEffectCategory.color
                        ), contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = move.moveEffectCategory.name,
                        // todo
                        fontSize = 12.sp, color = Color.White
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovesPreview() {
    MoveBoxColumn(BulbasaurMovesList)
}