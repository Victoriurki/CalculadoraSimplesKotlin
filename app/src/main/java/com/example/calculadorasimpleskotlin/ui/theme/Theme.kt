package com.example.calculadorasimpleskotlin.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable as Composable1

@Composable1
fun CalculadoraSimplesKotlinTheme(content: @Composable1 () -> Unit) {

    MaterialTheme(
        typography = Typography, content = content
    )
}