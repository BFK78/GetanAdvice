package com.example.getanadvice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.getanadvice.get_advice_feature.presentation.Screen
import com.example.getanadvice.get_advice_feature.presentation.advicescreen.screen.AdviseScreen
import com.example.getanadvice.get_advice_feature.presentation.common.MainArch
import com.example.getanadvice.get_advice_feature.presentation.mainscreen.MainScreen
import com.example.getanadvice.get_advice_feature.presentation.non_advice_screen.screen.NonScreenAdvice
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
    AnimatedNavHost(navController = navController, startDestination = Screen.MainScreen.route ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navHostController = navController)
        }
        composable(route = Screen.AdviceScreen.route) {
            AdviseScreen()
        }
        composable(route = Screen.NonAdviceScreen.route) {
            NonScreenAdvice()
        }
    }
}