package com.chema.practicapracticaa.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuarios(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var nombre: String,
    var apellido: String,
    var usuario: String,
    var telefono: String,
    var email: String,
    var password: String,
    val isPremium: Boolean
)
