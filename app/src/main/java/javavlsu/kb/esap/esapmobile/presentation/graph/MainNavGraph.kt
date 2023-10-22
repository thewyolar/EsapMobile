package javavlsu.kb.esap.esapmobile.presentation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import javavlsu.kb.esap.esapmobile.presentation.navigation.Screen
import javavlsu.kb.esap.esapmobile.presentation.ui.main.AppointmentBookingScreen
import javavlsu.kb.esap.esapmobile.presentation.ui.main.HomeScreen
import javavlsu.kb.esap.esapmobile.presentation.ui.main.AppointmentsScreen
import javavlsu.kb.esap.esapmobile.presentation.ui.main.ResultsScreen

@Composable
fun MainScreenNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Screen.Main.Home.route) {
            HomeScreen(navigateToSignIn = {
                rootNavController.popBackStack()
                rootNavController.navigate(Graph.Root.root)
            })
        }
        composable(route = Screen.Main.AppointmentBooking.route) {
            AppointmentBookingScreen()
        }
        composable(route = Screen.Main.Appointments.route) {
            AppointmentsScreen()
        }
        composable(route = Screen.Main.Results.route) {
            ResultsScreen()
        }
    }
}