package com.example.calculator_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator_1.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    var lastNumric = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onBackClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar = binding.dataTv.text.toString().last()
            if (lastChar.isDigit()) {
                onEqual()
            }
        } catch (e: Exception) {
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }


    fun onOperatorClick(view: View) {
        if (!stateError && lastNumric) {
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumric = false
            onEqual()
        }
    }


    fun onClearClick(view: View) {
        binding.dataTv.text = ""
        lastNumric = false
    }


    fun onAllClearClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastDot = false
        lastNumric = false
        binding.resultTv.visibility = View.GONE
    }


    fun onEqualClick(view: View) {
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)
    }


    fun onDigitClick(view: View) {

        if (stateError) {
            binding.dataTv.text = (view as Button).text
            stateError = false
        } else {
            binding.dataTv.append((view as Button).text)
        }
        lastNumric = true
        onEqual()
    }


    fun onEqual() {
        if (lastNumric && !stateError) {
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()
            } catch (ex: java.lang.ArithmeticException) {
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNumric = false
            }

        }


    }
}