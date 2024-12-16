package com.chema.practicapracticaa.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanciaRetrofit {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val api: ServicioApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServicioApi::class.java)
    }
}