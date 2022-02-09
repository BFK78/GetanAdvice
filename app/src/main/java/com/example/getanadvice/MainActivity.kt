package com.example.getanadvice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.getanadvice.get_advice_feature.presentation.Screen
import com.example.getanadvice.get_advice_feature.presentation.advicescreen.screen.AdviseScreen
import com.example.getanadvice.get_advice_feature.presentation.mainscreen.screen.MainScreen
import com.example.getanadvice.get_advice_feature.presentation.non_advice_screen.screen.NonScreenAdvice
import com.example.getanadvice.get_advice_feature.presentation.splash.screen.GetAnAdviseSplashScreen
import com.example.getanadvice.ui.theme.GetAnAdviceTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            GetAnAdviceTheme {
                NavigationContainer(
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationContainer(navController: NavHostController) {
    AnimatedNavHost(navController = navController, startDestination = Screen.SplashScreen.route ) {

        composable(route = Screen.SplashScreen.route) {
            GetAnAdviseSplashScreen(navHostController = navController)
        }

        composable(route = Screen.MainScreen.route,
        exitTransition = {
            null
        }
        ) {
            MainScreen(navHostController = navController)
        }
        composable(route = Screen.AdviceScreen.route,
        enterTransition = {
            null
        }) {
            AdviseScreen()
        }
        composable(route = Screen.NonAdviceScreen.route, enterTransition = {
            null
        }) {
            NonScreenAdvice()
        }
    }
}