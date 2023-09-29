package com.example.calculadorasimpleskotlin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.SharedPreferences


class CalculadoraViewModelFactory(private val context: Context, private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculadoraViewModel::class.java)) {
            val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            return CalculadoraViewModel(context, sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

