package com.example.calculadorasimpleskotlin


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.calculadorasimpleskotlin.ui.theme.CalculadoraSimplesKotlinTheme
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    private val db: FirebaseFirestore = Firebase.firestore

    private val viewModel: CalculadoraViewModel by viewModels {
        CalculadoraViewModelFactory(this, db)
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
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)


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


