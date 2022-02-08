package com.example.getanadvice.get_advice_feature.presentation.mainscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.getanadvice.R
import com.example.getanadvice.get_advice_feature.presentation.common.MainArch

@Composable
fun MainScreen(
    navHostController: NavHostController
) {
    MainArch(text = stringResource(id = R.string.home_advise)) {
        navHostController.popBackStack()
        navHostController.navigate(it)
    }
}