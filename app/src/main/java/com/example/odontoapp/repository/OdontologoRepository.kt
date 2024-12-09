package com.example.odontoapp.repository

import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OdontologoRepository {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.20.24:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getOdontologos(): List<Odontologo> {
        return apiService.getOdontologos()
    }
}
