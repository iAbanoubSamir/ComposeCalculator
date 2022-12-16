package com.compose.calculator.ui.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.compose.calculator.CalculatorAction
import com.compose.calculator.CalculatorOperation
import com.compose.calculator.CalculatorState

class MainViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Number -> enterNumbers(action.number)
            is CalculatorAction.Operation -> enterOperation(action.operation)
        }
    }

    private fun performCalculation() {
        val numberOne = state.numberOne.toDoubleOrNull()
        val numberTwo = state.numberTwo.toDoubleOrNull()

        if (numberOne != null && numberTwo != null) {
            val result = when (state.operation) {
                CalculatorOperation.Add -> numberOne + numberTwo
                CalculatorOperation.Divide -> numberOne / numberTwo
                CalculatorOperation.Multiply -> numberOne * numberTwo
                CalculatorOperation.Subtract -> numberOne - numberTwo
                null -> return
            }
            state = state.copy(
                numberOne = result.toString().take(10),
                numberTwo = "",
                operation = null
            )
        }
    }

    private fun enterDecimal() {
        if (
            state.operation == null && !state.numberOne.contains(".")
            && state.numberOne.isNotBlank()
        ) {
            state = state.copy(
                numberOne = state.numberOne + "."
            )
            return
        }
        if (
            !state.numberTwo.contains(".") && state.numberTwo.isNotBlank()
        ) {
            state = state.copy(
                numberOne = state.numberTwo + "."
            )
        }
    }

    private fun performDeletion() {
        when {
            state.numberTwo.isNotBlank() -> state =
                state.copy(
                    numberTwo = state.numberTwo.dropLast(1)
                )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.numberOne.isNotBlank() -> state = state.copy(
                numberOne = state.numberOne.dropLast(1)
            )
        }
    }

    private fun enterNumbers(number: Int) {
        if (state.operation == null) {
            if (state.numberOne.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                numberOne = state.numberOne + number
            )
            return
        }
        if (state.numberTwo.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            numberTwo = state.numberTwo + number
        )
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.numberOne.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}