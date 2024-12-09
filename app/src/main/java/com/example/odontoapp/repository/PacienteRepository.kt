package com.example.odontoapp.repository

import com.example.app.network.RetrofitInstance
import com.example.odontoapp.model.Paciente


class PacienteRepository {

    suspend fun getPacientes(): List<Paciente> {
        return RetrofitInstance.api.getPacientes()
    }
}