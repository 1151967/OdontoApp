package com.example.odontoapp.model

data class CitaRequest(
    val paciente: PacienteRequest,
    val odontologo: OdontologoRequest,
    val tratamiento: String,
    val descripcion: String,
    val fecha: String,
    val valor: Double,
    val pagado: Boolean
)

data class PacienteRequest(
    val id: Int
)

data class OdontologoRequest(
    val id: Int
)