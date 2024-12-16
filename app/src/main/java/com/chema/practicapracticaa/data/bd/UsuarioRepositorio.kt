package com.chema.practicapracticaa.data.bd

import com.chema.practicapracticaa.modelo.Usuarios

class UsuarioRepositorio(private val usuarioDao: UsuarioDao) {
    suspend fun registrarUsuario(usuarios: Usuarios) {
        usuarioDao.registrarUsuarios(usuarios)
    }

    suspend fun login(email: String, password: String): Usuarios? {
        return usuarioDao.login(email, password)
    }

    suspend fun buscarUsuarioPorNombreYEmail(usuarios: String, email: String): Usuarios? {
        return usuarioDao.buscarUsuarioPorNombreYEmail(usuarios, email)
    }

    suspend fun actualizarUsuario(usuarios: Usuarios) {
        usuarioDao.actualizarUsuario(usuarios)
    }

    suspend fun obtenerUsuarioPorId(id: Int): Usuarios? {
        return usuarioDao.obtenerUsuarioPorId(id)
    }
}