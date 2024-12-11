package com.example.odontoapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odontoapp.model.Cita
import com.example.odontoapp.model.CitaRequest
import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.repository.CitaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CitaViewModel(private val repository: CitaRepository) : ViewModel() {

    // Flow para citas
    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas

    // Flow para pacientes
    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes

    // Flow para odontólogos
    private val _odontologos = MutableStateFlow<List<Odontologo>>(emptyList())
    val odontologos: StateFlow<List<Odontologo>> = _odontologos

    // Método para obtener citas
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

    // Método para obtener pacientes
    fun fetchPacientes() {
        viewModelScope.launch {
            try {
                val result = repository.getPacientes()
                _pacientes.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Método para obtener odontólogos
    fun fetchOdontologos() {
        viewModelScope.launch {
            try {
                val result = repository.getOdontologos()
                _odontologos.value = result
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