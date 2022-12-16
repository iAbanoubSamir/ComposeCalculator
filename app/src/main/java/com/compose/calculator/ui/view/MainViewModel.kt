package com.compose.calculator.ui.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.compose.calculator.CalculatorState

class MainViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set
}