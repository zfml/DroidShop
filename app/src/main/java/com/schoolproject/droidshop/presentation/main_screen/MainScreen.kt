package com.schoolproject.droidshop.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.schoolproject.droidshop.presentation.navigation.AppNavHost
import com.schoolproject.droidshop.presentation.navigation.Destination
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel





@SuppressLint("RestrictedApi")
@Composable
fun MainScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToSignInScreen: () -> Unit,
) {
    val navController = rememberNavController()
    val isLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

//    LaunchedEffect(isLoggedIn) {
//        if (isLoggedIn == false) {
//            navigateToSignInScreen()
//        }
//    }

    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }



    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
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
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        
        AppNavHost(
            navController = navController,
            startDestination = Destination.HOME,
            modifier = Modifier.padding(innerPadding),
            navigateToLoginScreen = navigateToSignInScreen
        )
    }









}
