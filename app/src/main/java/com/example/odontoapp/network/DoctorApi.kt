package com.example.odontoapp.network

import com.example.odontoapp.model.Odontologo
import retrofit2.http.GET

interface ApiService {
    @GET("api/personas/odontologos")
    suspend fun getOdontologos(): List<Odontologo>
}
