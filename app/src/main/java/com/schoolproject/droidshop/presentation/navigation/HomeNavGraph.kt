package com.schoolproject.droidshop.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.presentation.favourite_screen.FavouriteScreen
import com.schoolproject.droidshop.presentation.home_screen.HomeScreen
import com.schoolproject.droidshop.presentation.profile.ProfileScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable


enum class Destination(
    val route: String,
    val label: String,
    val icon: Int,
    val iconActive: Int,
    val contentDescription: String
) {
    HOME(
        route = HomeScreen.toString(),
        label = "Home",
        icon = R.drawable.icon_home_outlined,
        iconActive = R.drawable.icon_home_filled,
        contentDescription = "Home"
    ),
    FAVOURITE(
        route = SearchScreen.toString(),
        label = "Favourite",
        icon = R.drawable.icon_favourite_outlined,
        iconActive = R.drawable.icon_favourite_filled,
        contentDescription = "Favourite"
    ),
    PROFILE(
        route = ProfileScreen.toString(),
        label = "Profile",
        icon = R.drawable.icon_profile_outlined,
        iconActive = R.drawable.icon_profile_filled,
        contentDescription = "Profile"
    ),
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    navigateToLoginScreen: () -> Unit,
    navigateToProductDetailScreen: (String) -> Unit,
    navigateToCartScreen: () -> Unit

) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> HomeScreen(
                        modifier,
                        navigateToProductDetailScreen,
                        navigateToCartScreen
                    )
                    Destination.FAVOURITE ->  {
                        FavouriteScreen()
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
                HomeScreen(
                    navigateToProductDetailScreen = navigateToProductDetailScreen,
                    navigateToCartScreen = navigateToCartScreen
                )
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
