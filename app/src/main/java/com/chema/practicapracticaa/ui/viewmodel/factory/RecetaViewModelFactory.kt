package com.chema.practicapracticaa.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chema.practicapracticaa.data.api.RecetaRepositorio
import com.chema.practicapracticaa.ui.viewmodel.RecetaViewModel


class RecetaViewModelFactory(private val repositorio: RecetaRepositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecetaViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
