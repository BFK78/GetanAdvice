package com.example.getanadvice.get_advice_feature.presentation.mainscreen.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.getanadvice.R
import com.example.getanadvice.get_advice_feature.presentation.common.MainArch
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(
    navHostController: NavHostController
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.apply {
            setSystemBarsColor(
                color = Color(0xFF7ACCA5)
            )
            setNavigationBarColor(Color.White)
        }
    }

    MainArch(text = stringResource(id = R.string.home_advise)) {
        navHostController.popBackStack()
        navHostController.navigate(it)
    }
}