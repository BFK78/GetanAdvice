package com.example.getanadvice.get_advice_feature.presentation.advicescreen.screen

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.getanadvice.get_advice_feature.presentation.advicescreen.AdviceViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collect

@Composable
fun AdviseScreen(
    viewModel: AdviceViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val alpha = remember {
        Animatable(0f)
    }

    val systemUiController = rememberSystemUiController()

    SideEffect{
        systemUiController.setSystemBarsColor(
            color = Color(0xFF16C1A2)
        )
    }

    LaunchedEffect(key1 = viewModel.toastEvent) {
        viewModel.toastEvent.collect {
            Toast.makeText(context, "Some Error happened $it", Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant),
        contentAlignment = Alignment.Center
    ) {
        if (viewModel.state.value.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.secondary
            )
        } else {
            LaunchedEffect(key1 = true) {
                alpha.animateTo(1f, animationSpec = tween(1000))
            }
            Text(
                text = viewModel.state.value.advice?.advice ?: "Some error just happened!!",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(alpha = alpha.value)
            )
        }
    }
}

// Add a small splash screen
