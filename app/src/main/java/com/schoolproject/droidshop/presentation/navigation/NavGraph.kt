package com.schoolproject.droidshop.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.schoolproject.droidshop.presentation.home_screen.HomeScreen
import com.schoolproject.droidshop.presentation.sign_in_screen.SignInScreen
import com.schoolproject.droidshop.presentation.sign_up_screen.SignUpScreen
import com.schoolproject.droidshop.presentation.splash_screen.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen
    )
    {


        composable<SplashScreen> {
            SplashScreen(
                navigateToHomeScreen = {
                    navController.navigate(HomeScreen) {
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


        composable<SignInScreen> {
            SignInScreen(
                navigateToHomeScreen = {
                    navController.navigate(HomeScreen) {
                        popUpTo(SignInScreen){
                            inclusive = true
                        }
                    }
                },
                navigateToSignUpScreen = {
                    navController.navigate(SignUpScreen)
                }
            )
        }


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
                     navController.navigate(SignInScreen)
                 }
             )
        }

        composable<HomeScreen> {
              HomeScreen(
                  navigateToSignInScreen = {
                      navController.navigate(SignInScreen) {
                          popUpTo(HomeScreen){
                              inclusive = true
                          }
                      }
                  }
              )
        }


    }
}

@Serializable
object SignInScreen

@Serializable
object SignUpScreen

@Serializable
object HomeScreen

@Serializable
object SplashScreen
