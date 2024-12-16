package com.chema.practicapracticaa.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chema.practicapracticaa.data.api.RecetaRepositorio
import com.chema.practicapracticaa.ui.viewmodel.DetalleRecetaViewModel

class DetalleRecetaViewModelFactory(private val repositorio: RecetaRepositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleRecetaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetalleRecetaViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
