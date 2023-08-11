package com.example.vanguardcardcreator.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vanguardcardcreator.Route

@Composable
fun Navigator(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Route.MAIN_MENU
    ) {
        composable(route = Route.MAIN_MENU) {
            MainScreen(navController = navController)
        }

        composable(
            route = Route.CREATE_UNIT,
            arguments = listOf(
                navArgument("Card ID") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { edit_argument ->
            edit_argument.arguments?.getInt("Card ID")?.let { UnitMakerScreen(cardId = it) }
        }

        composable(
            route = Route.CREATE_ORDER,
            arguments = listOf(
                navArgument("Card ID") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { edit_argument ->
            edit_argument.arguments?.getInt("Card ID")?.let { OrderMakerScreen(cardId = it) }
        }

        composable(
            route = Route.CREATE_MARKER,
            arguments = listOf(
                navArgument("Card ID") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { edit_argument ->
            edit_argument.arguments?.getInt("Card ID")?.let { MarkerMakerScreen(cardId = it) }
        }

        composable(route = Route.CREATE_NEW_CARD)
        {
            CardTypeSelectionScreen(navController)
        }
    }
}

