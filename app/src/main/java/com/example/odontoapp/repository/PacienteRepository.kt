package com.example.odontoapp.repository

import com.example.app.network.RetrofitInstance
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.model.PacienteRequest
import com.example.odontoapp.network.ApiService


class PacienteRepository {

    suspend fun getPacientes(): List<Paciente> {
        return RetrofitInstance.api.getPacientes()
    }

    suspend fun agregarPaciente(paciente: Paciente): Paciente {
        return RetrofitInstance.api.agregarPaciente(paciente)
    }
}
