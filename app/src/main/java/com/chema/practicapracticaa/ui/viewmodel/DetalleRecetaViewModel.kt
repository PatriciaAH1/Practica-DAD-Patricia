package com.chema.practicapracticaa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chema.practicapracticaa.data.api.RecetaRepositorio
import com.chema.practicapracticaa.modelo.Receta
import kotlinx.coroutines.launch

class DetalleRecetaViewModel(private val repositorio: RecetaRepositorio) : ViewModel() {

    private val _recetaDetalles = MutableLiveData<Receta>()
    val recetaDetalles: LiveData<Receta> = _recetaDetalles

    fun buscarDetallesDeReceta(id: String) {
        viewModelScope.launch {
            _recetaDetalles.value = repositorio.obtenerDetallesReceta(id)
        }
    }
}