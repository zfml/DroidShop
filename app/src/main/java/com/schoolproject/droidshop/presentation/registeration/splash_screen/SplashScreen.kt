package com.schoolproject.droidshop.presentation.registeration.splash_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.joinAll

@Composable
fun SplashScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit
) {

    val isLoggedIn by authViewModel.isUserLoggedIn.collectAsState()
    when (isLoggedIn) {
        null -> {
            // still loading; you can show a full‐screen spinner if you want
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        true -> {
            LaunchedEffect(Unit) {
                navigateToHomeScreen()
            }
        }
        false -> {
            LaunchedEffect(Unit) {
                navigateToSignInScreen()
            }
            }
        }

}