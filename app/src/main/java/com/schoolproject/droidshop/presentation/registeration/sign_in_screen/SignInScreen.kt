package com.schoolproject.droidshop.presentation.registeration.sign_in_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel

@Composable
fun SignInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen:() -> Unit,
    navigateToSignUpScreen: () -> Unit
) {
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.signIn(email.trim(), password.trim())
        }) {
            Text("Sign In")
        }

        TextButton(onClick = {
            navigateToSignUpScreen()
        }) {
            Text("Don't have an account? Sign up")
        }


        when(authState){
            is Resource.Error -> {
                Text((authState as Resource.Error).message ?: "Error", color = Color.Red)

            }
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                        navigateToHomeScreen()
                }
            }

            is Resource.Idle -> {}
        }

    }
}
