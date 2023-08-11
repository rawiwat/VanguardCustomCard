package com.example.vanguardcardcreator.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vanguardcardcreator.CardType
import com.example.vanguardcardcreator.IconResource
import com.example.vanguardcardcreator.Label.Companion.BLITZ
import com.example.vanguardcardcreator.Label.Companion.ORDER
import com.example.vanguardcardcreator.Nation
import com.example.vanguardcardcreator.R
import com.example.vanguardcardcreator.allNation
import com.example.vanguardcardcreator.all_order
import com.example.vanguardcardcreator.getBase
import com.example.vanguardcardcreator.getGradeStandard
import com.example.vanguardcardcreator.getLabel
import com.example.vanguardcardcreator.getLabelNation
import com.example.vanguardcardcreator.getNameBarStandard
import com.example.vanguardcardcreator.model.Order

@Composable
fun OrderMakerScreen(cardId: Int) {
//will make call for a card from sql database using id that was passed in
    val context = LocalContext.current
    Surface(Modifier.fillMaxSize()) {
        Column {
            Row(horizontalArrangement = Arrangement.Center) {
                val emptyOrder = Order(0,"Name_Here","",CardType.ORDER,"","None","",0, ORDER,false,"")
                val sampleOrder = Order(1,"บะลั่กๆอุ๋งๆ","เมื่อเล่นการ์ดนี้",CardType.ORDER,"",Nation.DARK_STATES,"", 4,
                    BLITZ,false,"เงอะ")
                OrderCardDisplay(if (cardId != 0) sampleOrder else emptyOrder, context)
                LazyColumn(verticalArrangement = Arrangement.Center) {
                    items(allNation) {nation->
                        NationSelection(name = nation, context = context)
                    }
                }
                LazyColumn() {
                    items(all_order) {
                        OrderTypeSelection(name = it, context = context)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun OrderCardDisplay(order: Order, context: Context) {
    var currentNation by rememberSaveable {
        mutableStateOf(order.nation)
    }

    var currentLabel by rememberSaveable {
        mutableStateOf(order.label)
    }

    var currentGrade by rememberSaveable {
        mutableStateOf(order.grade)
    }

    val currentType by rememberSaveable {
        mutableStateOf(order.type)
    }

    var currentName by rememberSaveable {
        mutableStateOf(order.name)
    }

        DisposableEffect(currentNation) {
            val nationReceiver =  object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val nationReceived = intent?.getStringExtra("change_nation")
                    if (nationReceived != null) currentNation = nationReceived
                }
            }
            context.registerReceiver(nationReceiver, IntentFilter("change_nation"))
            onDispose {
                context.unregisterReceiver(nationReceiver)
            }
        }

        DisposableEffect(currentLabel) {
            val orderReceiver =  object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val orderReceived = intent?.getStringExtra("change_order")
                    if (orderReceived != null) currentLabel = orderReceived
                }
            }
            context.registerReceiver(orderReceiver, IntentFilter("change_order"))
            onDispose {
                context.unregisterReceiver(orderReceiver)
            }
        }

        Surface(
            modifier = Modifier
                .height(409.dp)
                .width(280.5.dp)
        ) {
            /*SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(card.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                loading = {
                    CircularProgressIndicator()
                },
                contentScale = ContentScale.FillBounds
            )*/

            Image(
                painter = painterResource(id = R.drawable.sample_order),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Image(
                painter = painterResource(id = getBase(currentLabel)),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(top = 370.dp),
            )

            Image(
                painter = painterResource(id = getGradeStandard(currentNation,currentType)),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 235.dp, bottom = 350.dp)
            )

            Image(
                painter = painterResource(id = getNameBarStandard(currentNation,currentType)),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 367.dp, bottom = 0.dp, end = 0.dp, start = 0.dp),
                contentScale = ContentScale.FillWidth
            )

            Image(
                painter = painterResource(id = getLabel(currentLabel)),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 367.dp, end = 220.dp, bottom = 16.dp),
                contentScale = ContentScale.Inside
            )

            Image(
                painter = painterResource(id = R.drawable.effect_box_2),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 150.dp)

            )

            CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
                Box(
                    modifier = Modifier
                        .padding(top = 300.dp, start = 10.dp,end = 20.dp)
                ) {
                    CardText(order.text, IconResource)
                }
            }


            BasicTextField(
                value = currentGrade.toString(),
                onValueChange = {currentGrade = it.toInt()},
                modifier = Modifier
                    .padding(end = 236.5.dp, bottom = 362.dp),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.impact)),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )

            Text(
                text = currentGrade.toString(),
                modifier = Modifier
                    .padding(end = 236.5.dp, bottom = 362.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.impact)),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    drawStyle = Stroke(
                        width = 2f,
                        miter = 2f,
                    )
                ),
                maxLines = 1
            )

            BasicTextField(
                value = currentName,
                onValueChange = {currentName = it},
                modifier = Modifier
                    .padding(top = 370.dp, start = 5.dp)
                    .size(50.dp),
                textStyle = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontFamily = FontFamily(Font(R.font.imperial)),
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                ),
                maxLines = 1
            )

            if(allNation.contains(currentNation)) {
                Image(
                    painter = painterResource(id = getLabelNation(currentNation)),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 381.dp, start = 98.dp, end = 98.dp)
                )
            }
        }
}

@Composable
fun OrderTypeSelection(name: String, context: Context) {
    Image(
        painter = painterResource(id = getLabel(name)),
        contentDescription = null,
        modifier = Modifier.clickable {
            val intent = Intent("change_order")
            intent.putExtra("change_order", name)
            context.sendBroadcast(intent)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OrderMakerPreview() {
    OrderMakerScreen(cardId = 1)
}