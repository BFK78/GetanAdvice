package com.example.getanadvice.get_advice_feature.data.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class SlipDto(
    val advice: String,
    val id: Int
)

