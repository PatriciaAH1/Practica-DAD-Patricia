package com.chema.practicapracticaa.data.api

import com.chema.practicapracticaa.modelo.Receta


class RecetaRepositorio {
    private val api = InstanciaRetrofit.api

    suspend fun obtenerRecetasPorLetra(letra: String): List<Receta> {
        return api.obtenerRecetasPorLetra(letra).meals ?: emptyList()
    }

    suspend fun obtenerDetallesReceta(id: String): Receta? {
        return api.obtenerDetallesReceta(id).meals?.firstOrNull()
    }
}