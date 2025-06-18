package com.schoolproject.droidshop.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    authViewModel : AuthViewModel = hiltViewModel(),
    navigateToSignInScreen: () -> Unit
){


    val isLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == false) {
            navigateToSignInScreen()
        }
    }
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
    ){
        Text("Welcome to Droid Shop")
        Button(
            onClick = {
                authViewModel.signOut()
            }
        ) {
            Text("Logout")
        }
    }
}