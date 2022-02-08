package com.example.getanadvice.get_advice_feature.data.network.dto

import com.example.getanadvice.get_advice_feature.domain.model.Advice
import kotlinx.serialization.Serializable

@Serializable
data class AdviceDto(
    val slip: SlipDto
) {
    fun toAdvice(): Advice = Advice(slip.id, slip.advice)
}
