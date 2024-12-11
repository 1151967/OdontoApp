package com.example.odontoapp.model

// Estados posibles para el resultado de la operación
sealed class Resultado {
    object Inicial : Resultado()
    object Cargando : Resultado()
    data class Exito(val mensaje: String) : Resultado()
    data class Error(val mensaje: String) : Resultado()
}
