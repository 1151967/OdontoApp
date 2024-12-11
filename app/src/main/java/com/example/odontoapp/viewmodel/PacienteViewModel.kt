package com.example.odontoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.model.Resultado
import com.example.odontoapp.repository.PacienteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PacienteViewModel(private val repository: PacienteRepository) : ViewModel() {

    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Lista filtrada de pacientes basada en la búsqueda
    val filteredPacientes: StateFlow<List<Paciente>> = combine(_pacientes, _searchQuery) { pacientes, query ->
        if (query.isBlank()) {
            pacientes
        } else {
            pacientes.filter { it.nombre.contains(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

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

    // Actualizar la búsqueda
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    var agregarPacienteEstado = mutableStateOf<Resultado>(Resultado.Inicial)
        private set

    fun agregarPaciente(paciente: Paciente) {
        viewModelScope.launch {
            try {
                // Cambiar el estado a "Cargando" al iniciar el proceso
                agregarPacienteEstado.value = Resultado.Cargando

                // Lógica para agregar el paciente
                val response = repository.agregarPaciente(paciente)

                // Actualizar la lista local con el nuevo paciente
                _pacientes.value = _pacientes.value + response

                // Actualizar el estado a "Exito"
                agregarPacienteEstado.value = Resultado.Exito("Paciente agregado exitosamente: ${response.nombre}")
            } catch (e: Exception) {
                // En caso de error, actualizar el estado a "Error"
                agregarPacienteEstado.value = Resultado.Error("Error al agregar paciente: ${e.message}")
            } finally {
                // Resetear el estado después de un tiempo
                kotlinx.coroutines.delay(2000) // 2 segundos
                agregarPacienteEstado.value = Resultado.Inicial
            }
        }
    }
}
