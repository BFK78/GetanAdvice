package com.example.getanadvice.get_advice_feature.domain.use_cases

import com.example.getanadvice.core.Resources
import com.example.getanadvice.get_advice_feature.domain.model.Advice
import com.example.getanadvice.get_advice_feature.domain.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow

class GetAnAdviceUserCase(
    private val repository: AdviceRepository
) {

    operator fun invoke(): Flow<Resources<Advice>> {
        return repository.getAnAdvice()
    }
}