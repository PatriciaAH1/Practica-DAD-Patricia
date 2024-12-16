package com.chema.practicapracticaa.data.api

import com.chema.practicapracticaa.modelo.RecetaRespuesta
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicioApi {
    @GET("search.php")
    suspend fun obtenerRecetasPorLetra(@Query("f") letra: String): RecetaRespuesta

    @GET("lookup.php")
    suspend fun obtenerDetallesReceta(@Query("i") id: String): RecetaRespuesta
}