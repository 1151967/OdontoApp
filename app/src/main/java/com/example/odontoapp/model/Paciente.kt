package com.example.odontoapp.model

data class Paciente(
    val id: Int,
    val nombre: String,
    val cedula: String,
    val fechaNacimiento: String,
    val gmail: String? = null,
    val direccion: String? = null,
    val tipopersona: TipoPersona,
    val especialidad: Especialidad,
    val serviciosRecibidos: List<Servicio>? = null
)

data class TipoPersona(
    val id: Int,
    val descripcion: String? = null
)

data class Especialidad(
    val id: Int,
    val descripcion: String? = null
)

data class Servicio(
    val id: Int,
    val nombre: String,
    val costo: Double
)
