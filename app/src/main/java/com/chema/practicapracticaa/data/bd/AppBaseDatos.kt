package com.chema.practicapracticaa.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chema.practicapracticaa.modelo.Usuarios

@Database(entities = [Usuarios::class], version = 1, exportSchema = false)
abstract class AppBaseDatos : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppBaseDatos? = null

        fun obtenerBaseDatos(context: Context): AppBaseDatos {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppBaseDatos::class.java,
                    "bd_patricia"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}