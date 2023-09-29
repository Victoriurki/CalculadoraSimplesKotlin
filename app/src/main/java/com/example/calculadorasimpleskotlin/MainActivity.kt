package com.example.calculadorasimpleskotlin


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.calculadorasimpleskotlin.ui.theme.CalculadoraSimplesKotlinTheme


class MainActivity : ComponentActivity() {
    private val sharedPreferences by lazy {
        getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    }

    private val viewModel: CalculadoraViewModel by viewModels {
        CalculadoraViewModelFactory(this, sharedPreferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apply {
            getInvalidExpressionMessageEvent().observe(this@MainActivity) { shouldShow ->
                if (shouldShow != null && shouldShow) {
                    showInvalidExpressionMessage()
                }
            }
        }

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val savedExpression = sharedPreferences.getString("currentExpression", null)

        setContent {
            CalculadoraSimplesKotlinTheme {
                CalculatorScreen(viewModel)
            }
        }
    }

    private fun showInvalidExpressionMessage() {
        Toast.makeText(this, getString(R.string.invalid_expression_message), Toast.LENGTH_SHORT)
            .show()
    }
}


