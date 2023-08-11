package com.example.vanguardcardcreator.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.vanguardcardcreator.Ability
import com.example.vanguardcardcreator.CardTextSymbol
import com.example.vanguardcardcreator.CardType
import com.example.vanguardcardcreator.IconResource
import com.example.vanguardcardcreator.Label
import com.example.vanguardcardcreator.Nation
import com.example.vanguardcardcreator.Route
import com.example.vanguardcardcreator.model.Card
import com.example.vanguardcardcreator.model.Unit

val sample = Unit(
    0,"คนที่คุณก็รู้ว่าใคร",
    "${CardTextSymbol.AUTO} ${CardTextSymbol.VG_CIRCLE}: เมื่อยูนิทนี้โจมตี , ยูนิทนี้ได้รับ ${CardTextSymbol.POWER}+10000 จนจบเทิร์น", CardType.UNIT,
    "https://i.ibb.co/ncpFGwb/Kekera.png",Nation.STOICHEIA,"บีสต์",
    Label.NORMAL,"8000","5000","None",false,Ability.TRIPLE_DRIVE)

@Composable
fun MainScreen(navController: NavController) {

    val cards = mutableListOf<Card>()
    cards.add(sample)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Card Maker Vanguard TH",
                fontSize = 30.sp
            )
            LazyColumn(modifier = Modifier) {
                items(cards) {
                    CardDisplay(navController = navController, card = it)
                }
            }

            Card(
                modifier = Modifier
                    .clickable { navController.navigate(Route.CREATE_NEW_CARD) }
            ) {
                Text(
                    text = "+Create New Card",
                    modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun CardDisplay(navController: NavController,card: Card) {
    Surface(
        modifier = Modifier
            .height(409.dp)
            .width(280.5.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(card.image)
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .clickable { navController.navigate(Route().passUnitId(card.id)) }
                .fillMaxSize(),
            loading = {
                CircularProgressIndicator()
            },
            contentScale = ContentScale.FillBounds
        )
        CardText(card.text, IconResource)
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(navController = NavController(LocalContext.current))
}