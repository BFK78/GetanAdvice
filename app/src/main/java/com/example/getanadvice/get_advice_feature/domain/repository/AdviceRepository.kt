package com.example.getanadvice.get_advice_feature.domain.repository

import com.example.getanadvice.core.Resources
import com.example.getanadvice.get_advice_feature.domain.model.Advice
import kotlinx.coroutines.flow.Flow

interface AdviceRepository {

    fun getAnAdvice(): Flow<Resources<Advice>>

}