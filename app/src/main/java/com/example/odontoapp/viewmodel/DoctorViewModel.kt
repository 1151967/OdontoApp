package com.example.odontoapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.repository.OdontologoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class OdontologoViewModel(private val repository: OdontologoRepository) : ViewModel() {

    private val _odontologos = MutableStateFlow<List<Odontologo>>(emptyList())
    val odontologos: StateFlow<List<Odontologo>> = _odontologos

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
}
