package com.example.odontoapp.model

data class Paciente(
    val id: Int,
    val nombre: String,
    val cedula: String,
    val fechaNacimiento: String,
    val gmail: String? = null
)