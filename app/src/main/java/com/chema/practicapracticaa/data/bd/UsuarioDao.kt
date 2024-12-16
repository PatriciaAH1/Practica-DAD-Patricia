package com.chema.practicapracticaa.data.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.chema.practicapracticaa.modelo.Usuarios

@Dao
interface UsuarioDao {

    @Insert
    suspend fun registrarUsuarios(usuarios: Usuarios)

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): Usuarios?

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND email = :email")
    suspend fun buscarUsuarioPorNombreYEmail(usuario: String, email: String): Usuarios?

    @Update
    suspend fun actualizarUsuario(usuarios: Usuarios)

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun obtenerUsuarioPorId(id: Int): Usuarios?


}