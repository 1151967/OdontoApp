package com.example.odontoapp.network

import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.model.Paciente
import retrofit2.http.GET

interface ApiService {
    @GET("personas/odontologos")
    suspend fun getOdontologos(): List<Odontologo>

    @GET("personas/pacientes")
    suspend fun getPacientes(): List<Paciente>
}
