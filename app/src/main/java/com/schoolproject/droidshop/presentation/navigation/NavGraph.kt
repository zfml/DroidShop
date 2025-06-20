package com.schoolproject.droidshop.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.schoolproject.droidshop.presentation.home_screen.HomeScreen
import com.schoolproject.droidshop.presentation.main_screen.MainScreen
import com.schoolproject.droidshop.presentation.registeration.sign_in_screen.SignInScreen
import com.schoolproject.droidshop.presentation.registeration.sign_up_screen.SignUpScreen
import com.schoolproject.droidshop.presentation.registeration.splash_screen.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        enterTransition = { fadeIn(animationSpec = tween(100)) },
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        popEnterTransition = { fadeIn(animationSpec = tween(100)) },
        popExitTransition = { fadeOut(animationSpec = tween(100)) },
        startDestination = MainScreen
    )
    {
        composable<SplashScreen> {
            SplashScreen(
                navigateToHomeScreen = {
                    navController.navigate(MainScreen) {
                        popUpTo(SplashScreen){
                            inclusive = true
                        }
                    }
                },
                navigateToSignInScreen = {
                    navController.navigate(SignInScreen) {
                        popUpTo(SplashScreen){
                            inclusive = true
                        }
                    }
                }
            )
        }



        composable<MainScreen> {
            MainScreen(
                navigateToSignInScreen = {
                    navController.navigate(SignInScreen) {
                        popUpTo(MainScreen){
                            inclusive = true
                        }
                    }
                }
            )
        }




        composable<SignInScreen>(
            content = {
                SignInScreen(
                    navigateToHomeScreen = {
                        navController.navigate(MainScreen) {
                            popUpTo(SignInScreen){
                                inclusive = true
                            }
                        }
                    },
                    navigateToSignUpScreen = {
                        navController.navigate(SignUpScreen)
                    }
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }



        )


        composable<SignUpScreen> {
             SignUpScreen(
                 navigateToHomeScreen = {
                     navController.navigate(HomeScreen) {
                         popUpTo(SignUpScreen) {
                             inclusive = true
                         }
                     }
                 },
                 navigateToSignInScreen = {
                     navController.navigate(SignInScreen) {
                         popUpTo(SignUpScreen) {
                             inclusive = true
                         }
                     }
                 }
             )
        }
    }
}

@Serializable
object MainScreen

@Serializable
object SignInScreen

@Serializable
object SignUpScreen

@Serializable
object SplashScreen

@Serializable
object ProfileScreen





