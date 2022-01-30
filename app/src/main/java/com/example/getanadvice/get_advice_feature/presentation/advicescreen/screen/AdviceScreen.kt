package com.example.getanadvice.get_advice_feature.presentation.advicescreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.getanadvice.R

@Composable
fun AdviseScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            text = stringResource(id = R.string.home_advise),
            style = MaterialTheme.typography.h5
        )
    }
}
