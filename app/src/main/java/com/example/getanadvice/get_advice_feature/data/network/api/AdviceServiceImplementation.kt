package com.example.getanadvice.get_advice_feature.data.network.api

import android.util.Log
import com.example.getanadvice.core.constants.Constant.URL
import com.example.getanadvice.get_advice_feature.data.network.dto.AdviceDto
import com.example.getanadvice.get_advice_feature.data.network.dto.SlipDto
import io.ktor.client.*
import io.ktor.client.request.*
import java.lang.Exception


class AdviceServiceImplementation(
    private val client: HttpClient
): AdviceService {
    override suspend fun getAdvice(): AdviceDto {
        return try {

            client.get {
                url(URL)
            }

        }catch (e: Exception) {
            Log.i("Error", e.message.toString())
            throw e
        }
    }
}