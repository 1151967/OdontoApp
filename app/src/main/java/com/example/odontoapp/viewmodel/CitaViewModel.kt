package com.example.odontoapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odontoapp.model.Cita
import com.example.odontoapp.model.CitaRequest
import com.example.odontoapp.repository.CitaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CitaViewModel(private val repository: CitaRepository) : ViewModel() {

    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas

    fun fetchCitas() {
        viewModelScope.launch {
            try {
                val result = repository.getCitas()
                _citas.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Método para crear una cita
    fun createCita(citaRequest: CitaRequest, onSuccess: (Cita) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val newCita = repository.createCita(citaRequest)
                onSuccess(newCita)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}