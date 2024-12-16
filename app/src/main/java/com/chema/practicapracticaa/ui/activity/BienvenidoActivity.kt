package com.chema.practicapracticaa.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.chema.practicapracticaa.databinding.ActivityBienvenidoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BienvenidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBienvenidoBinding
    private var mainActivityJob: Job? = null
    private var opcionSeleccionada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBienvenidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        establecerFunciones()
    }

    private fun establecerFunciones() {
        establecerModoOscuro()
        configurarNavegacion()
    }

    private fun establecerModoOscuro() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun configurarNavegacion() {
        mainActivityJob = CoroutineScope(Dispatchers.Main).launch {
            delay(6000)
            if (!opcionSeleccionada) {
                startActivity(Intent(this@BienvenidoActivity, MainActivity::class.java))
                finish()
            }
        }
        binding.textViewLogin.setOnClickListener {
            opcionSeleccionada = true
            mainActivityJob?.cancel()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.buttonRegister.setOnClickListener {
            opcionSeleccionada = true
            mainActivityJob?.cancel()
            startActivity(Intent(this, RegistroActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityJob?.cancel()
    }
}



/*class BienvenidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBienvenidoBinding
    private var mainActivityJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBienvenidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        establecerFunciones()
    }

    private fun establecerFunciones(){
        establecerModoOscuro()
        abrirMainActivity()
        abrirLoginActivity()
        abrirRegistroActivity()
    }

    private fun establecerModoOscuro(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun abrirMainActivity() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        mainActivityJob = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            if (isLoggedIn) {
                startActivity(Intent(this@BienvenidoActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@BienvenidoActivity, LoginActivity::class.java))
            }
            finish()
        }
    }


    private fun abrirLoginActivity() {
        binding.textViewLogin.setOnClickListener {
            mainActivityJob?.cancel()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun abrirRegistroActivity(){
        binding.buttonRegister.setOnClickListener {
            mainActivityJob?.cancel()
            startActivity(Intent(this, RegistroActivity::class.java))
            finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mainActivityJob?.cancel()
    }
}*/