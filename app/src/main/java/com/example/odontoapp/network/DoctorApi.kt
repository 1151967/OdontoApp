package com.example.odontoapp.network

import com.example.odontoapp.model.Cita
import com.example.odontoapp.model.CitaRequest
import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.model.Paciente
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("personas/odontologos")
    suspend fun getOdontologos(): List<Odontologo>

    @GET("personas/pacientes")
    suspend fun getPacientes(): List<Paciente>

    @GET("citas")
    suspend fun getCitas(): List<Cita>

    @POST("citas")
    suspend fun createCita(@Body cita: CitaRequest): Cita

    @POST("personas")
    suspend fun agregarPaciente(@Body paciente: Paciente): Paciente

}
