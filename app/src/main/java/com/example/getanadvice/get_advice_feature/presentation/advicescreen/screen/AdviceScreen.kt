package com.example.getanadvice.get_advice_feature.presentation.advicescreen.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.getanadvice.R
import com.example.getanadvice.get_advice_feature.presentation.advicescreen.AdviceViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun AdviseScreen(
    viewModel: AdviceViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.toastEvent) {
        viewModel.toastEvent.collect {
            Log.i("basim", it)
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
            Text(
                text = viewModel.state.value.advice?.advice ?: "Some error just happened!!",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


// go to the main branch

// remove animation from navigation

// change the status bar color

// Add a small splash screen
