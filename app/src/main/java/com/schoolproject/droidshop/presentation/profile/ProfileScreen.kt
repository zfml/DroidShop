package com.schoolproject.droidshop.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit
){

    val state = authViewModel.authState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = {
                navigateToLoginScreen()
//                authViewModel.signOut()
            }
        ) {
            Text("Logout")
        }

        when(state.value) {
            is Resource.Error -> {
                Text(state.value.message.toString())
            }
            is Resource.Idle -> {

            }
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Success -> {
                navigateToLoginScreen()
            }
        }
    }
}