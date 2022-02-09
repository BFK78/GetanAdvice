package com.example.getanadvice.get_advice_feature.presentation.splash.screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getanadvice.R
import com.example.getanadvice.get_advice_feature.presentation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun GetAnAdviseSplashScreen(navHostController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    val color = remember {
        Animatable(Color.White)
    }

    LaunchedEffect(key1 = true) {
        color.animateTo(Color(0xFFFFC060), animationSpec = tween(1000))
        delay(1500)
        navHostController.popBackStack()
        navHostController.navigate(Screen.MainScreen.route)
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            Color(0xFF16C1A2)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant),
        contentAlignment = Alignment.Center
    ) {

        Text(
            stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h5.copy(
                color = color.value,
                fontSize = 32.sp
            )
        )

    }
}