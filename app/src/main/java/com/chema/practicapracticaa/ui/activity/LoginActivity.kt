package com.chema.practicapracticaa.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chema.practicapracticaa.data.bd.AppBaseDatos
import com.chema.practicapracticaa.data.bd.UsuarioRepositorio
import com.chema.practicapracticaa.databinding.ActivityLoginBinding
import com.chema.practicapracticaa.modelo.Usuarios
import com.chema.practicapracticaa.ui.viewmodel.UsuarioViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = AppBaseDatos.obtenerBaseDatos(application).usuarioDao()
        usuarioViewModel = UsuarioViewModel(UsuarioRepositorio(userDao))

        establecerFunciones()
    }

    private fun establecerFunciones(){
        binding.buttonLogIn.setOnClickListener {
            logIn()
        }
        abrirRegistroActivity()
        abrirRestablecerContrasenaActivity()
    }

    private fun logIn(){
        if (validarCampos()) {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            usuarioViewModel.login(email, password) { usuarios ->
                if (usuarios != null) {
                    guardarEstadoDeSesion(usuarios)
                    Toast.makeText(this, "Bienvenido ${usuarios.nombre} ${usuarios.apellido}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validarCampos(): Boolean {
        with(binding) {
            when {
                editTextEmail.text.isNullOrEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches() -> {
                    editTextEmail.error = "Correo electrónico no válido"
                    return false
                }
                editTextPassword.text.isNullOrEmpty() || editTextPassword.text.length < 6 -> {
                    editTextPassword.error = "La contraseña debe tener al menos 6 caracteres"
                    return false
                }

                else -> {}
            }
        }
        return true
    }

    private fun abrirRegistroActivity() {
        binding.textViewRegisterHere.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
            finish()
        }
    }

    private fun abrirRestablecerContrasenaActivity() {
        binding.textViewForgetPassword.setOnClickListener {
            startActivity(Intent(this, RestablecerContrasenaActivity::class.java))
            finish()
        }
    }

    private fun guardarEstadoDeSesion(usuarios: Usuarios) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", true)
        editor.putInt("user_id", usuarios.id)
        editor.apply()
    }

}