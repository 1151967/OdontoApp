package com.example.odontoapp.repository

import com.example.app.network.RetrofitInstance
import com.example.odontoapp.model.Cita
import com.example.odontoapp.model.CitaRequest


class CitaRepository {
    private val apiService = RetrofitInstance.api

    suspend fun getCitas(): List<Cita> {
        return apiService.getCitas()
    }

    suspend fun createCita(citaRequest: CitaRequest): Cita {
        return apiService.createCita(citaRequest)
    }
}