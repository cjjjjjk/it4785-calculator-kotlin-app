package com.example.caculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private var tvResult: TextView? = null
    private var lastNumeric = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvResult)
    }

    fun onDigit(view: View) {
        tvResult?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvResult?.text = ""
        lastNumeric = false
    }

    fun onCE(view: View) {
        val currentText = tvResult?.text.toString()
        if (currentText.isNotEmpty()) {
            tvResult?.text = currentText.dropLast(1)
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvResult?.text.toString())) {
            tvResult?.append((view as Button).text)
            lastNumeric = false
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            val expression = tvResult?.text.toString()
            try {
                val result = evaluateExpression(expression).toInt()
                tvResult?.text = result.toString()
            } catch (e: Exception) {
                tvResult?.text = "Error"
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return value.isNotEmpty() && value.last() in listOf('+', '-', '*', '/')
    }

    private fun evaluateExpression(expression: String): Double {
        return ExpressionBuilder(expression).build().evaluate()
    }
}