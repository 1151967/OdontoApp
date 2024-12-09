package com.example.odontoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.odontoapp.repository.OdontologoRepository

class OdontologoViewModelFactory(
    private val repository: OdontologoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OdontologoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OdontologoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
