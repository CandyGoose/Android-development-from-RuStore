package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.appdetails.AppDetailsScreen
import com.example.myapplication.presentation.applist.AppListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    onShowMessage: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.APP_LIST,
        modifier = modifier
    ) {
        composable(Routes.APP_LIST) {
            AppListScreen(
                onAppClick = { appId ->
                    navController.navigate(Routes.appDetails(appId))
                },
                onShowMessage = onShowMessage
            )
        }
        composable(
            route = Routes.APP_DETAILS,
            arguments = listOf(navArgument("appId") { defaultValue = "" })
        ) { backStackEntry ->
            AppDetailsScreen(navController = navController)
        }
    }
}
