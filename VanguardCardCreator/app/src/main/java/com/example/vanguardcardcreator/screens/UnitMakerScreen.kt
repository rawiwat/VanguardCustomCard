package com.example.vanguardcardcreator.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.vanguardcardcreator.Ability
import com.example.vanguardcardcreator.CardTextSymbol
import com.example.vanguardcardcreator.CardType
import com.example.vanguardcardcreator.IconResource
import com.example.vanguardcardcreator.Label
import com.example.vanguardcardcreator.MainActivity
import com.example.vanguardcardcreator.R
import com.example.vanguardcardcreator.allNation
import com.example.vanguardcardcreator.byteArrayToBitmap
import com.example.vanguardcardcreator.getAbilityIcon
import com.example.vanguardcardcreator.getBase
import com.example.vanguardcardcreator.getFlag
import com.example.vanguardcardcreator.getGradeStandard
import com.example.vanguardcardcreator.getNameBarStandard
import com.example.vanguardcardcreator.model.Card
import com.example.vanguardcardcreator.model.Unit

val emptyUnit = Unit(0 ,"Name_Here", CardTextSymbol.VG_CIRCLE, CardType.UNIT,"","","Race_Here",
    Label.NORMAL,"0","0","none", false, "none")
@Composable
fun UnitMakerScreen(cardId: Int) {
//will make call for a card from sql database using id that was passed in
    val context = LocalContext.current
    val allSkill = listOf(Ability.BOOST,Ability.INTERCEPT,Ability.TWIN_DRIVE,Ability.TRIPLE_DRIVE)
    Surface(Modifier.fillMaxSize()) {
        Column {
            Row(horizontalArrangement = Arrangement.Center) {
                CardDisplay(if (cardId != 0) sample else emptyUnit, context)
                LazyColumn(verticalArrangement = Arrangement.Center) {
                    items(allNation) {
                        NationSelection(name = it, context = context)
                    }
                }

                LazyColumn {
                    items(allSkill) {skill ->
                        SkillSelection(name = skill, context = context)
                    }
                }
            }
            Button(onClick = { (context as? MainActivity)?.fetchImage(context) }) {
                Text("Click to Upload Image")
            }
        }
    }

}

@OptIn(ExperimentalTextApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun CardDisplay(card: Card, context: Context) {

    var currentImage by rememberSaveable {
        mutableStateOf<Bitmap?>(null)
    }
    var currentNation by rememberSaveable {
        mutableStateOf(card.nation)
    }

    val currentLabel by rememberSaveable {
        mutableStateOf(card.label)
    }

    var currentAbility by rememberSaveable {
        mutableStateOf(card.ability)
    }

    var currentGrade by rememberSaveable {
        mutableStateOf(card.grade)
    }

    var currentShield by rememberSaveable {
        mutableStateOf(card.shield)
    }

    var currentPower by rememberSaveable {
        mutableStateOf(card.power)
    }

    val currentType by rememberSaveable {
        mutableStateOf(card.type)
    }

    var currentName by rememberSaveable {
        mutableStateOf(card.name)
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

    DisposableEffect(currentAbility) {
        val skillReceiver =  object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val skillReceived = intent?.getStringExtra("change_skill")
                if (skillReceived != null) currentAbility = skillReceived
            }
        }
        context.registerReceiver(skillReceiver, IntentFilter("change_skill"))
        onDispose {
            context.unregisterReceiver(skillReceiver)
        }
    }

    DisposableEffect(currentAbility) {
        val imageReceiver =  object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val imageReceived = intent?.getByteArrayExtra("change_image")
                if (imageReceived != null) currentImage = byteArrayToBitmap(imageReceived)
            }
        }
        context.registerReceiver(imageReceiver, IntentFilter("change_image"))
        onDispose {
            context.unregisterReceiver(imageReceiver)
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

        if (currentImage == null) {
            Image(
                painter = painterResource(id = R.drawable.sample),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        } else {
            currentImage?.let {
                GlideImage(it, contentDescription = null,
                    contentScale = ContentScale.FillBounds)
            }
        }

        
        Image(
            painter = painterResource(id = getBase(currentLabel)),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
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
                .padding(top = 339.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.label_normal),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 355.dp, end = 220.dp, bottom = 34.dp),
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
                CardText(card.text, IconResource)
            }
        }

        Image(
            painter = painterResource(id = R.drawable.shield_symbol),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 242.dp, bottom = 80.dp)
        )

        BasicTextField(
            value = currentShield,
            onValueChange = {currentShield = it},
            modifier = Modifier
                .rotate(90f)
                .padding(top = 313.dp, end = 80.dp),
            textStyle = TextStyle(
                color = colorResource(id = R.color.light_yellow),
                fontFamily = FontFamily(Font(R.font.impact)),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = currentShield,
            modifier = Modifier
                .rotate(90f)
                .padding(top = 313.dp, end = 80.dp),
            style = TextStyle(
                color = colorResource(id = R.color.black),
                fontFamily = FontFamily(Font(R.font.impact)),
                fontWeight = FontWeight.ExtraBold,
                drawStyle = Stroke(
                    miter = 20f,
                    width = 2f,
                    join = StrokeJoin.Round
                ),
                textAlign = TextAlign.Center
            )
        )

        Box(
            modifier = Modifier
                .padding(end = 140.dp)
        ) {
            val topPadding = 373.dp
            val startPadding = 60.dp
            val containerSize = 40.dp
            val fontSize = 17.sp

            BasicTextField(
                value = currentPower,
                onValueChange = {currentPower = it},
                modifier = Modifier
                    .padding(top = topPadding, start = startPadding)
                    .size(40.dp),
                textStyle = TextStyle(
                    color = colorResource(id = R.color.yellow),
                    fontFamily = FontFamily(Font(R.font.impact)),
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize
                ),
                maxLines = 1
            )

            Text(
                text = currentPower,
                modifier = Modifier
                    .padding(top = topPadding, start = startPadding)
                    .size(containerSize),
                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    color = colorResource(id = R.color.black),
                    fontFamily = FontFamily(Font(R.font.impact)),
                    fontWeight = FontWeight.ExtraBold,
                    drawStyle = Stroke(
                        miter = 20f,
                        width = 2f,
                        join = StrokeJoin.Round
                    )
                ),
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontSize = fontSize
            )
        }

        BasicTextField(
            value = currentName,
            onValueChange = {currentName = it},
            modifier = Modifier
                .padding(top = 355.dp, start = 5.dp, bottom = 30.dp)
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

        BasicTextField(
            value = currentGrade.toString(),
            onValueChange = {currentGrade = it.toInt()},
            modifier = Modifier
                .padding(end = 236.5.dp, bottom = 362.dp),
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.impact)),
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            maxLines = 1
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

        Image(
            painter = painterResource(id = getAbilityIcon(currentAbility)),
            modifier = Modifier.padding(end = 235.dp, bottom = 303.dp),
            contentDescription = null
        )

        Image(
            painter = painterResource(id = R.drawable.crit_1),
            contentDescription = null,
            Modifier.padding(top = 385.dp, bottom = 15.dp,start = 140.dp,end = 130.dp)
        )
    }
}

@Composable
fun CardText(cardText: String, Icons: List<SkillIcon>) {
    val pattern = Regex("-([a-zA-Z]+)-")
    val parts = cardText.split(pattern)
    val textModifier = Modifier
    val textStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.imperial)),
        lineHeight = 10.sp,
        color = Color.Black
    )
    val fontSize = 12.sp

    Column {
        parts.forEachIndexed { index, part ->
            val isEmoji = index % 2 != 0
            if (isEmoji) {
                val emoji = Icons.find { it.name == part }
                if (emoji != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(emoji.image),
                            contentDescription = null,
                            modifier = Modifier.size(2.dp)
                        )
                        Spacer(modifier = Modifier.width(0.dp))
                        Text(
                            text = part,
                            modifier = textModifier,
                            style = textStyle,
                            fontSize = fontSize,
                        )
                    }
                } else {
                    Text(
                        text = part,
                        modifier = textModifier,
                        style = textStyle,
                        fontSize = fontSize,
                    )
                }
            } else {
                Text(
                    text = part,
                    modifier = textModifier,
                    style = textStyle,
                    fontSize = fontSize,
                )
            }
        }
    }
}

data class SkillIcon(val name: String, val image: Int)


@Composable
fun NationSelection(name: String,context: Context) {
    Image(
        painter = getFlag(name),
        contentDescription = null,
        modifier = Modifier.clickable {
            val intent = Intent("change_nation")
            intent.putExtra("change_nation",name)
            context.sendBroadcast(intent)
        }
    )
}

@Composable
fun SkillSelection(name: String,context: Context) {
    Image(
        painter = painterResource(id = getAbilityIcon(name)),
        contentDescription = null,
        modifier = Modifier.clickable {
            val intent = Intent("change_skill")
            intent.putExtra("change_skill",name)
            context.sendBroadcast(intent)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCardMaker() {
    UnitMakerScreen(0)
}

@Preview(showBackground = true)
@Composable
fun PreviewCardMaker2() {
    UnitMakerScreen(1)
}



