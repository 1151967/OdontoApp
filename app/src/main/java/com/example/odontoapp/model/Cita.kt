package com.example.odontoapp.model

data class Cita(
    val pacienteNombre: String?,
    val pacienteEdad: Int,
    val odontologoNombre: String,
    val odontologoEspecialidad: String,
    val tratamiento: String,
    val descripcion: String,
    val fecha: String,
    val diasRestantes: Int,
    val valor: Double,
    val pagado: Boolean
)