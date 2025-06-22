package com.schoolproject.droidshop.presentation.registeration.sign_in_screen

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.presentation.registeration.sign_up_screen.CustomOutlinedTextField
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
    var errorMessage by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(R.drawable.icon_visibility)
    else
        painterResource(R.drawable.icon_visibility_off)

    var isVisible by remember { mutableStateOf(false) }



        LoginContent(
            isLoading = isLoading,
            errorMessage = errorMessage,
            isVisible = isVisible,
            icon = icon,
            email = email,
            password = password,
            passwordVisibility = passwordVisibility,
            passwordError = false,
            onPasswordVisibilityChange = {
                passwordVisibility = !passwordVisibility
            },
            onEmailChange = {
                email = it
            },
            onPasswordChange = {
                password = it
            },
            onLoginClick = {
                viewModel.signIn(email.trim(), password.trim())

            },
            onSignUpClick = {
                navigateToSignUpScreen()
            }
        )

        when(authState){
            is Resource.Error -> {
                isLoading = false
                errorMessage = (authState as Resource.Error).message ?: "Error"

            }
            is Resource.Loading -> {
                isLoading = true

            }
            is Resource.Success -> {
                isLoading = false
                LaunchedEffect(Unit) {
                        navigateToHomeScreen()
                }
            }

            is Resource.Idle -> {}
        }
}


@Composable
fun LoginContent(
    isLoading: Boolean,
    errorMessage: String,
    isVisible: Boolean,
    icon: Painter,
    email: String,
    password: String,
    passwordVisibility: Boolean,
    passwordError: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(
                top = 100.dp,
                start = 32.dp,
                end = 32.dp
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "Welcome Back!",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp
        )

        Spacer(Modifier.height(16.dp))


        CustomOutlinedTextField(
            value = email,
            hint = stringResource(R.string.enter_your_email),
            onValueChanged = onEmailChange,
            trailingIcon = {},
            isError = false,
            errorMessage = errorMessage
        )
        CustomOutlinedTextField(
            value = password,
            hint = "Enter your password",
            onValueChanged = onPasswordChange,
            trailingIcon = {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onPasswordVisibilityChange()
                        }
                )
            },
            isError = passwordError,
            errorMessage = errorMessage,
            visualTransformation = if(passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "Forget Password!",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }


        LoadingButton(
            isLoading = isLoading,
            onLoginClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Don't have an account? Sign Up",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    onSignUpClick()
                }
        )

        Text(errorMessage)

    }

}

@Composable
fun LoadingButton(
    onLoginClick: () -> Unit,
    isLoading: Boolean // Pass the loading state from the parent composable
) {
    Button(
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        onClick = onLoginClick,
        enabled = !isLoading, // Disable button when loading
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}
