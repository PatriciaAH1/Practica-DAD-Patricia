package com.chema.practicapracticaa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chema.practicapracticaa.data.api.RecetaRepositorio
import com.chema.practicapracticaa.modelo.Receta
import kotlinx.coroutines.launch

class RecetaViewModel(private val repository: RecetaRepositorio) : ViewModel() {

    private val _recetas = MutableLiveData<List<Receta>>()
    val recetas: LiveData<List<Receta>> = _recetas

    private val _letraActual = MutableLiveData('a')
    val letraActual: LiveData<Char> = _letraActual

    init {
        buscarRecetasParaLetraActual()
    }

    fun buscarRecetasParaLetraActual() {
        viewModelScope.launch {
            _recetas.value = repository.obtenerRecetasPorLetra(_letraActual.value.toString())
        }
    }

    fun cambiarLetra(incremento: Boolean) {
        val nuevaLetra = if (incremento) _letraActual.value!! + 1 else _letraActual.value!! - 1
        if (nuevaLetra in 'a'..'z') {
            _letraActual.value = nuevaLetra
            buscarRecetasParaLetraActual()
        }
    }
}