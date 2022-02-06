package com.example.getanadvice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.getanadvice.get_advice_feature.presentation.common.MainArch
import com.example.getanadvice.ui.theme.GetAnAdviceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetAnAdviceTheme {
                MainArch()
            }
        }
    }
}