package com.chema.practicapracticaa.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chema.practicapracticaa.data.bd.AppBaseDatos
import com.chema.practicapracticaa.data.bd.UsuarioRepositorio
import com.chema.practicapracticaa.databinding.ActivityRegistroBinding
import com.chema.practicapracticaa.modelo.Usuarios
import com.chema.practicapracticaa.ui.viewmodel.UsuarioViewModel

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = AppBaseDatos.obtenerBaseDatos(application).usuarioDao()
        usuarioViewModel = UsuarioViewModel(UsuarioRepositorio(userDao))

        establecerFunciones()
    }

    private fun establecerFunciones(){
        binding.buttonRegister.setOnClickListener {
            registrarUsuario()
        }
        abrirLoginActivity()
    }

    private fun registrarUsuario(){
        if (validarCampos()) {
            val usuario = Usuarios(
                nombre = binding.editTextName.text.toString(),
                apellido = binding.editTextLastName.text.toString(),
                usuario = binding.editTextUsername.text.toString(),
                telefono = binding.editTextPhone.text.toString(),
                email = binding.editTextEmail.text.toString(),
                password = binding.editTextPassword.text.toString(),
                isPremium = true
            )

            usuarioViewModel.registrarUsuario(usuario, {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish()
                abrirBienvenidoActivity()
            }, { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun validarCampos(): Boolean {
        with(binding) {
            when {
                editTextName.text.isNullOrEmpty() -> {
                    editTextName.error = "El nombre no puede estar vacío"
                    return false
                }
                editTextLastName.text.isNullOrEmpty() -> {
                    editTextLastName.error = "El apellido no puede estar vacío"
                    return false
                }
                editTextUsername.text.isNullOrEmpty() -> {
                    editTextUsername.error = "El nombre de usuario no puede estar vacío"
                    return false
                }
                editTextPhone.text.isNullOrEmpty() -> {
                    editTextPhone.error = "El teléfono no puede estar vacío"
                    return false
                }
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

    private fun abrirLoginActivity(){
        binding.textViewLoginHere.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun abrirBienvenidoActivity(){
        startActivity(Intent(this, BienvenidoActivity::class.java))
    }
}