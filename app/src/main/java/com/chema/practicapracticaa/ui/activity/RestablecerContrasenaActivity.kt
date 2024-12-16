package com.chema.practicapracticaa.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chema.practicapracticaa.R
import com.chema.practicapracticaa.data.bd.AppBaseDatos
import com.chema.practicapracticaa.data.bd.UsuarioRepositorio
import com.chema.practicapracticaa.databinding.ActivityRestablecerContrasenaBinding
import com.chema.practicapracticaa.ui.viewmodel.UsuarioViewModel

class RestablecerContrasenaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestablecerContrasenaBinding
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestablecerContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userDao = AppBaseDatos.obtenerBaseDatos(application).usuarioDao()
        usuarioViewModel = UsuarioViewModel(UsuarioRepositorio(userDao))

        establecerFunciones()
    }

    private fun establecerFunciones(){
        binding.buttonCambiarContrasena.setOnClickListener {
            cambiarContrasena()
        }
        atras()
    }

    private fun cambiarContrasena() {
        val usuario = binding.editTextUsuario.text.toString()
        val email = binding.editTextEmail.text.toString()
        val nuevaContrasena = binding.editTextNuevaContrasena.text.toString()

        if (usuario.isNotEmpty() && email.isNotEmpty() && nuevaContrasena.length >= 6) {
            usuarioViewModel.actualizarContrasena(usuario, email, nuevaContrasena) { exito ->
                if (exito) {
                    Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                    abrirLoginActivity()
                } else {
                    Toast.makeText(this, "Usuario o email incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun atras(){
        binding.arrowBack.setOnClickListener {
            abrirLoginActivity()
        }
    }
}