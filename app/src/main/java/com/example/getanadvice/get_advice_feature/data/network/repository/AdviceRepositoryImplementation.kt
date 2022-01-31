package com.example.getanadvice.get_advice_feature.data.network.repository

import android.util.Log
import com.example.getanadvice.core.Resources
import com.example.getanadvice.get_advice_feature.data.network.api.AdviceService
import com.example.getanadvice.get_advice_feature.domain.model.Advice
import com.example.getanadvice.get_advice_feature.domain.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class AdviceRepositoryImplementation(
    private val service: AdviceService
): AdviceRepository {

    override fun getAnAdvice(): Flow<Resources<Advice>> = flow {
        emit(Resources.Loading(data = Advice(id = 0, "Loading....")))
        try {
            val advice = service.getAdvice().toAdvice()
            emit(Resources.Success(data = advice))
        }catch (e: Exception) {
            Log.i("Error" , e.message.toString())
            emit(Resources.Error(data = Advice(id = 0, "Loading...."), message = e.message.toString()))
        }
    }
}