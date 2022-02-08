package com.example.getanadvice.get_advice_feature.presentation

import com.example.getanadvice.core.constants.Constant.ADVICE_SCREEN_ROUTE
import com.example.getanadvice.core.constants.Constant.MAIN_SCREEN_ROUTE
import com.example.getanadvice.core.constants.Constant.NON_ADVICE_SCREEN_ROUTE

sealed class Screen(val route: String) {
    object AdviceScreen: Screen(ADVICE_SCREEN_ROUTE)
    object NonAdviceScreen: Screen(NON_ADVICE_SCREEN_ROUTE)
    object MainScreen: Screen(MAIN_SCREEN_ROUTE)

}
