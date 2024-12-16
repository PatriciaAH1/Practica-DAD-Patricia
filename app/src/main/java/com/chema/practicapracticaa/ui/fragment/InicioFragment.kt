package com.chema.practicapracticaa.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.chema.practicapracticaa.R
import com.chema.practicapracticaa.data.api.RecetaRepositorio
import com.chema.practicapracticaa.databinding.FragmentInicioBinding
import com.chema.practicapracticaa.ui.activity.DetalleRecetaActivity
import com.chema.practicapracticaa.ui.activity.RegistroActivity
import com.chema.practicapracticaa.ui.adaptador.RecetaAdaptador
import com.chema.practicapracticaa.ui.viewmodel.RecetaViewModel
import com.chema.practicapracticaa.ui.viewmodel.factory.RecetaViewModelFactory
import com.github.chrisbanes.photoview.PhotoView
import kotlin.math.max
import kotlin.math.min


class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecetaViewModel
    private lateinit var adaptador: RecetaAdaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInicioBinding.inflate(inflater, container, false)

        establecerFunciones()

        return binding.root
    }


    private fun establecerFunciones(){
        establecerViewModelyAdaptador()
        observarViewModelRecetas()
        cambiarLetraConFlechas()
        abrirDialog()
        verificarEstadoDeSesion()
    }

    private fun establecerViewModelyAdaptador() {
        val repositorio = RecetaRepositorio()
        val factory = RecetaViewModelFactory(repositorio)
        viewModel = ViewModelProvider(this, factory)[RecetaViewModel::class.java]

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        adaptador = RecetaAdaptador(
            isLoggedIn = isLoggedIn,
            onRecetaClick = { recipeId ->
                val intent = Intent(requireContext(), DetalleRecetaActivity::class.java)
                intent.putExtra("RECIPE_ID", recipeId)
                startActivity(intent)
            },
            onInfoClick = {
                mostrarInfoDialog()
            },
            onImageClick = { imageUrl ->
                mostrarZoomDialog(imageUrl)
            }
        )

        binding.recyclerViewRecipe.adapter = adaptador
    }


    private fun observarViewModelRecetas() {
        viewModel.letraActual.observe(requireActivity()) { letra ->
            binding.textViewFilterLetter.text = letra.toString()
        }

        viewModel.recetas.observe(requireActivity()) { recetas ->
            adaptador.submitList(recetas)
        }

    }

    private fun cambiarLetraConFlechas() {
        binding.arrowStart.setOnClickListener {
            viewModel.cambiarLetra(incremento = false)
        }
        binding.arrowEnd.setOnClickListener {
            viewModel.cambiarLetra(incremento = true)
        }
    }

    private fun abrirDialog(){
        binding.floatingInfo.setOnClickListener {
            mostrarInfoDialog()
        }
    }

    private fun mostrarInfoDialog() {
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_info, null) as LinearLayout

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogLayout)

        val dialog = builder.create()
        dialog.show()

        val buttonOK = inflater.inflate(R.layout.dialog_button, null) as Button
        buttonOK.setOnClickListener {
            dialog.dismiss()
        }

        val buttonRegistrar = inflater.inflate(R.layout.dialog_button_registrar, null) as Button
        buttonRegistrar.setOnClickListener {
            val intent = Intent(requireContext(), RegistroActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            dialog.dismiss()
        }

        dialogLayout.addView(buttonOK)
        dialogLayout.addView(buttonRegistrar)
    }

    private fun mostrarZoomDialog(imagenUrl: String) {
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_zoom_imagen, null)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogLayout)

        val dialog = builder.create()

        val photoView = dialogLayout.findViewById<PhotoView>(R.id.photoView)
        Glide.with(this)
            .load(imagenUrl)
            .placeholder(R.drawable.logopatricia)
            .error(R.drawable.ic_launcher_background)
            .into(photoView)

        dialog.show()

    }




    private fun verificarEstadoDeSesion() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            binding.floatingInfo.visibility = View.GONE
        } else {
            binding.floatingInfo.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}