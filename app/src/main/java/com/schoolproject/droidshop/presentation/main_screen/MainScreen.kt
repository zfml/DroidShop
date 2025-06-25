package com.schoolproject.droidshop.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.schoolproject.droidshop.presentation.navigation.AppNavHost
import com.schoolproject.droidshop.presentation.navigation.Destination
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel
import com.schoolproject.droidshop.ui.theme.poppinsFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RestrictedApi")
@Composable
fun MainScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToSignInScreen: () -> Unit,
    navigateToProductDetailScreen: (String) -> Unit,
    navigateToCartScreen: () -> Unit
) {
    val navController = rememberNavController()
    val isLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == false) {
            navigateToSignInScreen()
        }
    }

    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {


        },
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    val selected = selectedDestination == index
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                            }
                            selectedDestination = index
                        },
                        icon = {
                            if(selected) {
                                Icon(
                                    painter = painterResource(destination.iconActive),
                                    contentDescription = destination.label
                                )
                            }else {
                                Icon(
                                    painter = painterResource(destination.icon),
                                    contentDescription = destination.label
                                )
                            }

                        },
                        label = {
                            if(selected){
                                Text(
                                    text = destination.name,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }else {
                                Text(
                                    text = destination.name,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        
        AppNavHost(
            navController = navController,
            startDestination = Destination.HOME,
            modifier = Modifier
                .padding(innerPadding)

            ,

            navigateToLoginScreen = navigateToSignInScreen,
            navigateToProductDetailScreen = navigateToProductDetailScreen,
            navigateToCartScreen = navigateToCartScreen

        )
    }









}
