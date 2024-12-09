package com.example.odontoapp.repository

import com.example.app.network.RetrofitInstance
import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OdontologoRepository {
    private val apiService = RetrofitInstance.api

    suspend fun getOdontologos(): List<Odontologo> {
        return apiService.getOdontologos()
    }
}
