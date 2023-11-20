package com.example.calculadorasimpleskotlin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore


class CalculadoraViewModelFactory(private val context: Context, private val db: FirebaseFirestore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculadoraViewModel::class.java)) {
            return CalculadoraViewModel(context, db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

