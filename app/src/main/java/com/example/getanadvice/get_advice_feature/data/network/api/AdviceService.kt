package com.example.getanadvice.get_advice_feature.data.network.api

import com.example.getanadvice.get_advice_feature.data.network.dto.AdviceDto

interface AdviceService {

    suspend fun getAdvice(): AdviceDto

}