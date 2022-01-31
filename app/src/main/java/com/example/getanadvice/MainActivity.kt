package com.example.getanadvice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.getanadvice.get_advice_feature.presentation.advicescreen.AdviceViewModel
import com.example.getanadvice.get_advice_feature.presentation.common.ChoosingArc
import com.example.getanadvice.ui.theme.GetAnAdviceTheme
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetAnAdviceTheme {
                val initial = remember {
                    mutableStateOf(Offset.Zero)
                }

                Box(modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                        initial.value = it.boundsInRoot().bottomRight
                        Log.i("window", it.boundsInRoot().bottomRight.toString())
                    }) {
                    if (initial.value != Offset.Zero) {
                        ChoosingArc(
                            initial = initial.value
                        )
                    }
                }
            }
        }
    }
}