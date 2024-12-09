package com.example.odontoapp.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.repository.OdontologoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OdontologoViewModel(private val repository: OdontologoRepository) : ViewModel() {

    private val _odontologos = MutableStateFlow<List<Odontologo>>(emptyList()) // Lista completa de odontólogos
    val odontologos: StateFlow<List<Odontologo>> = _odontologos

    private val _searchQuery = MutableStateFlow("") // Query de búsqueda
    val searchQuery: StateFlow<String> = _searchQuery

    // Cargar los odontólogos desde el repositorio
    fun fetchOdontologos() {
        viewModelScope.launch {
            try {
                // Simula la llamada a repositorio y carga los odontólogos
                val result = repository.getOdontologos()
                Log.d("OdontologoViewModel", "Odontólogos obtenidos: ${result.size}")
                _odontologos.value = result // Guardar en el estado
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Filtrar odontólogos según la búsqueda
    fun filteredOdontologos(): List<Odontologo> {
        val query = _searchQuery.value.trim() // Obtener la consulta de búsqueda
        return if (query.isEmpty()) {
            _odontologos.value // Devuelve todos los odontólogos si no hay búsqueda
        } else {
            _odontologos.value.filter { it.nombre.contains(query, ignoreCase = true) }
            // Filtra por nombre, ignorando mayúsculas/minúsculas
        }
    }

    // Actualizar la búsqueda
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}


