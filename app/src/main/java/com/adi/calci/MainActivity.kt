package com.adi.calci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adi.calci.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAC.setOnClickListener{
            binding.tvExpression.text = ""
            binding.tvResult.text = ""
        }

        binding.btnDEL.setOnClickListener {
            var currentExpression = binding.tvExpression.text.toString()
            if(currentExpression.isNotEmpty()){
                var newExpression = currentExpression.substring(0, currentExpression.length - 1)
                binding.tvExpression.text = newExpression
            }
        }

        binding.btnAdd.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            if((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1])) && currentExpression.isNotEmpty()){
                var newExpression = currentExpression + "+"
                binding.tvExpression.text = newExpression
            }
        }
        binding.btnSubtract.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            if((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1])) && currentExpression.isNotEmpty()){
                var newExpression = currentExpression + "-"
                binding.tvExpression.text = newExpression
            }
        }
        binding.btnMultiply.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            if((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1])) && currentExpression.isNotEmpty()){
                var newExpression = currentExpression + "x"
                binding.tvExpression.text = newExpression
            }
        }
        binding.btnDivide.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            if((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1])) && currentExpression.isNotEmpty()){
                var newExpression = currentExpression + "/"
                binding.tvExpression.text = newExpression
            }
        }

        binding.btn0.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "0"
            binding.tvExpression.text = newExpression
        }
        binding.btn1.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "1"
            binding.tvExpression.text = newExpression
        }
        binding.btn2.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "2"
            binding.tvExpression.text = newExpression
        }
        binding.btn3.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "3"
            binding.tvExpression.text = newExpression
        }
        binding.btn4.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "4"
            binding.tvExpression.text = newExpression
        }
        binding.btn5.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "5"
            binding.tvExpression.text = newExpression
        }
        binding.btn6.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "6"
            binding.tvExpression.text = newExpression
        }
        binding.btn7.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "7"
            binding.tvExpression.text = newExpression
        }
        binding.btn8.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "8"
            binding.tvExpression.text = newExpression
        }
        binding.btn9.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "9"
            binding.tvExpression.text = newExpression
        }
        binding.btnEquals.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            if(currentExpression != ""){
                var newExpression = evaluateMathExpression(currentExpression)
                binding.tvResult.text = newExpression.toString()
            }

        }
    }
}


val forbiddenSuccessiveOperators = listOf('+','-','x','/',' ')

fun evaluateMathExpression(expression: String): Double {
    val sanitizedExpression = expression.replace(" ", "") // Remove any spaces from the expression
    val numbers = mutableListOf<Double>()
    val operators = mutableListOf<Char>()
    var currentNumber = StringBuilder()

    for (char in sanitizedExpression) {
        if (char.isDigit() || char == '.') {
            currentNumber.append(char)
        } else {
            if (currentNumber.isNotEmpty()) {
                numbers.add(currentNumber.toString().toDouble())
                currentNumber.clear()
            }

            if (char == '(') {
                operators.add(char)
            } else if (char == ')') {
                while (operators.isNotEmpty() && operators.last() != '(') {
                    val result = performCalculation(numbers, operators)
                    numbers.add(result)
                }
                operators.removeLastOrNull() // Remove the '(' operator
            } else {
                while (operators.isNotEmpty() && hasPrecedence(char, operators.last())) {
                    val result = performCalculation(numbers, operators)
                    numbers.add(result)
                }
                operators.add(char)
            }
        }
    }

    if (currentNumber.isNotEmpty()) {
        numbers.add(currentNumber.toString().toDouble())
    }

    while (operators.isNotEmpty()) {
        val result = performCalculation(numbers, operators)
        numbers.add(result)
    }

    return numbers.last()
}

fun performCalculation(numbers: MutableList<Double>, operators: MutableList<Char>): Double {
    val operator = operators.removeLastOrNull()
    val operand2 = numbers.removeLastOrNull()
    val operand1 = numbers.removeLastOrNull()

    if (operator != null && operand2 != null && operand1 != null) {
        return when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            'x' -> operand1 * operand2
            '/' -> operand1 / operand2
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    } else {
        throw IllegalArgumentException("Invalid expression")
    }
}

fun hasPrecedence(op1: Char, op2: Char): Boolean {
    if (op2 == '(' || op2 == ')') return false
    return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')
}