package javavlsu.kb.esap.esapmobile.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import javavlsu.kb.esap.esapmobile.ui.graph.MainScreenNavGraph
import javavlsu.kb.esap.esapmobile.ui.navigation.BottomNavigationBar

@Composable
fun MainScreen(navHostController: NavHostController = rememberNavController()) {
    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navHostController)
    }) { paddingValues ->
        MainScreenNavGraph(
            navController = navHostController,
            paddingValues = paddingValues
        )
    }
}