package com.compose.calculator

data class CalculatorState(
    val numberOne: String = "",
    val numberTwo: String = "",
    val operation: CalculatorOperation? = null
)
