package com.chema.practicapracticaa.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chema.practicapracticaa.ui.fragment.InicioFragment
import com.chema.practicapracticaa.ui.fragment.PerfilFragment
import com.chema.practicapracticaa.R
import com.chema.practicapracticaa.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        establecerFunciones()
    }


    private fun establecerFunciones(){
        reemplazarFragmento(InicioFragment())
        opcionesDeMenu()
        verificarEstadoDeSesion()
    }

    private fun verificarEstadoDeSesion() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (!isLoggedIn) {
            binding.bottomNavigation.menu.findItem(R.id.perfil).isVisible = false
        }
    }

    private fun opcionesDeMenu(){
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.inicio -> reemplazarFragmento(InicioFragment())
                R.id.perfil -> reemplazarFragmento(PerfilFragment())
                else -> {
                    Toast.makeText(this, "No se pudo acceder al fragmento", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    private fun reemplazarFragmento(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}