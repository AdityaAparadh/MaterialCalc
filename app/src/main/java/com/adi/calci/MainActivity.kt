package com.adi.calci

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adi.calci.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    fun errorToast(){
        Toast.makeText( this ,"Invalid Expression",Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("expression", binding.tvExpression.text.toString())
        outState.putString("result", binding.tvResult.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println(binding.tvExpression.toString())

        if (savedInstanceState != null) {
            binding.tvExpression.text = savedInstanceState.getString("expression")
            binding.tvResult.text = savedInstanceState.getString("result")
        }

        binding.btnDEL.setOnClickListener {
            var currentExpression = binding.tvExpression.text.toString()
            if(currentExpression.isNotEmpty()){
                var newExpression = currentExpression.substring(0, currentExpression.length - 1)
                binding.tvExpression.text = newExpression
            }
        }

        binding.btnDEL.setOnLongClickListener {
            binding.tvExpression.text = ""
            binding.tvResult.text = ""
            true
        }

        binding.btnAdd.setOnClickListener{
            if(binding.tvExpression.text.toString().isNotEmpty()) {
                var currentExpression = binding.tvExpression.text.toString()
                if ((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1]))) {
                    var newExpression = currentExpression + "+"
                    binding.tvExpression.text = newExpression
                }
            }
        }
        binding.btnSubtract.setOnClickListener{
            if(binding.tvExpression.text.toString().isNotEmpty()) {
                var currentExpression = binding.tvExpression.text.toString()
                if ((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1]))) {
                    var newExpression = currentExpression + "-"
                    binding.tvExpression.text = newExpression
                }
           }
        }
        binding.btnMultiply.setOnClickListener{
            if(binding.tvExpression.text.toString().isNotEmpty()) {
                var currentExpression = binding.tvExpression.text.toString()
                if ((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1]))) {
                    var newExpression = currentExpression + "×"
                    binding.tvExpression.text = newExpression
                }
            }
        }

        binding.btnDivide.setOnClickListener{
            if(binding.tvExpression.text.toString().isNotEmpty()) {
                var currentExpression = binding.tvExpression.text.toString()
                if ((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1]))) {
                    var newExpression = currentExpression + "÷"
                    binding.tvExpression.text = newExpression
                }
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
        binding.btnDot.setOnClickListener{
            if(binding.tvExpression.text.toString().isNotEmpty()) {
                var currentExpression = binding.tvExpression.text.toString()
                if ((!forbiddenSuccessiveOperators.contains(currentExpression[currentExpression.length - 1]))) {
                    var newExpression = currentExpression + "."
                    binding.tvExpression.text = newExpression
                }
            }
        }
        binding.btnRightBracket.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + ")"
            binding.tvExpression.text = newExpression
        }
        binding.btnLeftBracket.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            var newExpression = currentExpression + "("
            binding.tvExpression.text = newExpression
        }


        binding.btnEqualTo.setOnClickListener{
            var currentExpression = binding.tvExpression.text.toString()
            if(currentExpression != ""){
                var newExpression = evaluateMathExpression(currentExpression, this)
                binding.tvResult.text = newExpression
            }
        }
    }
}


val forbiddenSuccessiveOperators = listOf('+','-','×','÷',' ','.')

fun evaluateMathExpression(expression: String, context: Context): String {
    val sanitizedExpression = expression.replace("×", "*").replace("÷", "/")

    return try {
        val result = ExpressionBuilder(sanitizedExpression).build().evaluate()
        formatResult(result)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Invalid Expression", Toast.LENGTH_SHORT).show()
        "0.0"
    }
}

fun formatResult(result: Double): String {
    return if (result % 1 == 0.0) {
        result.toInt().toString()
    } else {
        result.toString()
    }
}


