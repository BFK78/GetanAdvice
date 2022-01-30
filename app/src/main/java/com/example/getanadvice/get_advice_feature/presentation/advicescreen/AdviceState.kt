package com.example.getanadvice.get_advice_feature.presentation.advicescreen

import com.example.getanadvice.get_advice_feature.domain.model.Advice

data class AdviceState(
    val isLoading: Boolean= false,
    val advice: Advice? = null
)