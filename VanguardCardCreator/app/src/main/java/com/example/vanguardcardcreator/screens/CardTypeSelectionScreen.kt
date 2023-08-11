package com.example.vanguardcardcreator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.vanguardcardcreator.Route

@Composable
fun CardTypeSelectionScreen(navController: NavController) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Select Card Type")
            CardTypeSelectionButton(route = Route.CREATE_UNIT, navController = navController, text = "Unit")
            CardTypeSelectionButton(route = Route.CREATE_ORDER, navController = navController, text = "Order")
            CardTypeSelectionButton(route = Route.CREATE_MARKER,navController,"Marker")
        }
    }
}

@Composable
fun CardTypeSelectionButton(route:String,navController: NavController,text:String) {
    Button(onClick = { navController.navigate(route) }) {
        Text(text = text)
    }
}

@Preview
@Composable
fun CardTypeSelectionPreview() {
    CardTypeSelectionScreen(navController = NavController(LocalContext.current))
}