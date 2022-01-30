package com.example.getanadvice.get_advice_feature.presentation.advicescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getanadvice.core.Resources
import com.example.getanadvice.get_advice_feature.domain.use_cases.GetAnAdviceUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdviceViewModel @Inject constructor(
    private val getAnAdviceUserCase: GetAnAdviceUserCase
): ViewModel() {

    private val _state = mutableStateOf(AdviceState())
    val state: State<AdviceState> = _state

    fun getAnAdvice() {
        getAnAdviceUserCase().onEach {
            when(it) {
                is Resources.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                }
                is Resources.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        advice = it.data
                    )
                }
                is Resources.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}