package javavlsu.kb.esap.esapmobile.presentation.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import javavlsu.kb.esap.esapmobile.presentation.theme.Blue

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navigationItems = listOf(
        Screen.Main.Home,
        Screen.Main.Appointment,
        Screen.Main.Profile,
        Screen.Main.Settings
    )

    var selectedScreen by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, screen ->
            val isSelected = (selectedScreen == index)
            val color = if (isSelected) Blue else Color.Gray

            NavigationBarItem(
                modifier = Modifier.clickable { },
                icon = {
                    Icon(
                        imageVector = screen.icon!!,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier
                            .size(30.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = color,
                    )
                },
                selected = isSelected,
                onClick = {
                    if (navController.currentBackStack.value.size >= 2) {
                        navController.popBackStack()
                    }
                    selectedScreen = index
                    navController.navigate(screen.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )
        }
    }
}
