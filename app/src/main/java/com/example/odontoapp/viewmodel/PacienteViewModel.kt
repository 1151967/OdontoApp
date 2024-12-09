package com.example.odontoapp.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.repository.PacienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PacienteViewModel(private val repository: PacienteRepository) : ViewModel() {

    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes

    // Cargar los pacientes desde el repositorio
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
}
