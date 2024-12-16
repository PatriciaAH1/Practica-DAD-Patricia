package com.chema.practicapracticaa.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chema.practicapracticaa.data.bd.UsuarioRepositorio
import com.chema.practicapracticaa.modelo.Usuarios
import kotlinx.coroutines.launch

class UsuarioViewModel(private val usuarioRepositorio: UsuarioRepositorio) : ViewModel() {

    fun registrarUsuario(usuarios: Usuarios, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                usuarioRepositorio.registrarUsuario(usuarios)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al registrar")
            }
        }
    }

    fun login(email: String, password: String, onResult: (Usuarios?) -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioRepositorio.login(email, password)
            onResult(usuario)
        }
    }

    fun actualizarContrasena(usuarios: String, email: String, nuevaContrasena: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioRepositorio.buscarUsuarioPorNombreYEmail(usuarios, email)
            if (usuario != null) {
                usuario.password = nuevaContrasena
                usuarioRepositorio.actualizarUsuario(usuario)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun obtenerUsuarioPorId(id: Int, onResult: (Usuarios?) -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioRepositorio.obtenerUsuarioPorId(id)
            onResult(usuario)
        }
    }

    fun actualizarInformacion(usuario: Usuarios, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                usuarioRepositorio.actualizarUsuario(usuario)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }



}
