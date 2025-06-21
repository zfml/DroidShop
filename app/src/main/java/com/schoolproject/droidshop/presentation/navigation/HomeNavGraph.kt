package com.schoolproject.droidshop.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.schoolproject.droidshop.presentation.home_screen.HomeScreen
import com.schoolproject.droidshop.presentation.profile.ProfileScreen
import kotlinx.serialization.Serializable


enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME( HomeScreen.toString(), "Home", Icons.Default.Home, "Home"),
    SEARCH(SearchScreen.toString(), "Search", Icons.Default.Search, "Search"),
    PROFILE(ProfileScreen.toString(),"Profile",Icons.Default.AccountCircle,"Account")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    navigateToLoginScreen: () -> Unit,
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> HomeScreen(modifier)
                    Destination.SEARCH ->  {
                        Text("Search Screen")
                    }
                    Destination.PROFILE -> {
                        ProfileScreen {
                           navigateToLoginScreen()
                        }
                    }
                }
            }
        }


        composable<HomeScreen> (
            content = {
                HomeScreen()
            }
        )


        composable<SearchScreen> {
            Text("I am a search screen")
        }






    }
}




@Serializable
object HomeScreen

@Serializable
object SearchScreen
