package com.example.calculadorasimpleskotlin


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.calculadorasimpleskotlin.ui.theme.CalculadoraSimplesKotlinTheme


class MainActivity : ComponentActivity() {
    private var viewModel: CalculadoraViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModels<CalculadoraViewModel>().value

        viewModel.apply {
            this?.getInvalidExpressionMessageEvent()?.observe(this@MainActivity) { shouldShow ->
                if (shouldShow != null && shouldShow) {
                    this@MainActivity.showInvalidExpressionMessage()
                }
            }
        }

        setContent {
            CalculadoraSimplesKotlinTheme{
                CalculatorScreen(viewModel)
            }
        }
    }

    private fun showInvalidExpressionMessage(): Unit =
        Toast.makeText(this, getString(R.string.invalid_expression_message), Toast.LENGTH_SHORT)
            .show()
}

