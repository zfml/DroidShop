package com.schoolproject.droidshop.presentation.registeration.sign_up_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schoolproject.droidshop.R
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen:() -> Unit,
    navigateToSignInScreen: () -> Unit
) {
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(R.drawable.icon_visibility)
    else
        painterResource(R.drawable.icon_visibility_off)




    LaunchedEffect(authState) {
        if (authState is Resource.Success) {
            navigateToHomeScreen()
        }
    }


        SignUpContent(
            email = email,
            onEmailChanged = {
                email = it
            },
            username = username,
            password = password,
            onUsernameChanged = {},
            onPasswordChanged = {
                password = it
            },
            passwordVisibility = passwordVisibility,
            onPasswordVisibilityChange = {
                passwordVisibility = !passwordVisibility
            },
            onSignUpClicked = {
                viewModel.signUp(email.trim(), password.trim(), username.trim())

            },
            onLoginClicked = {
                navigateToSignInScreen()
            },
            passwordError = false,
            emailError = false,
            icon = icon
        )



        when(authState){
            is Resource.Error -> {

                Text((authState as Resource.Error).message ?: "Error", color = Color.Red)


            }
            is Resource.Loading -> {
                CircularProgressIndicator()

            }
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navigateToHomeScreen()
                }
            }

            is Resource.Idle -> {}
        }



}
@Composable
fun SignUpContent(
    email: String,
    username: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    icon: Painter,
    passwordVisibility: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onSignUpClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    passwordError: Boolean,
    emailError: Boolean
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp
        )

        Text(
            text = "Register or sign up and we'll get started",
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,

        )

        CustomOutlinedTextField(
            value =  email,
            hint = stringResource(R.string.enter_your_email),
            onValueChanged = onEmailChanged,
            trailingIcon = {},
            isError = emailError,
            errorMessage = stringResource(R.string.error_email)
        )

        CustomOutlinedTextField(
            value = username,
            hint = "Enter your username",
            onValueChanged = onUsernameChanged,
            trailingIcon = {},
            isError = false,
            errorMessage = null
        )

        CustomOutlinedTextField(
            value = password,
            hint = "Enter your password",
            onValueChanged = onPasswordChanged,
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
            errorMessage = "Passwords must not contain spaces",
            visualTransformation = if(passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()

        )

        Button(
            modifier = Modifier
                .padding(top = 32.dp)
                .height(55.dp)
                .fillMaxWidth()
            ,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = onSignUpClicked
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.background,
                modifier =  Modifier
            )
        }


        TextButton(onClick = { onLoginClicked() }) {
            Text("Already have an account? Sign in")
        }

    }

}


@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean,
    errorMessage: String?,
    hint: String = ""

) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color(0xFFCAC8C8),
            unfocusedIndicatorColor = Color(0xFFCAC8C8)
        ),
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.outline,
                fontSize = 15.sp
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        trailingIcon = {
            trailingIcon()
        },
        visualTransformation = visualTransformation,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
        ),
        isError = isError,
        supportingText = {
            if(isError) {
                if(errorMessage != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    )
}