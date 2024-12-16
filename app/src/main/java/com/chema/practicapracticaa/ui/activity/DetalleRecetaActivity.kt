package com.chema.practicapracticaa.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chema.practicapracticaa.data.api.RecetaRepositorio
import com.chema.practicapracticaa.databinding.ActivityDetalleRecetaBinding
import com.chema.practicapracticaa.modelo.Receta
import com.chema.practicapracticaa.ui.viewmodel.DetalleRecetaViewModel
import com.chema.practicapracticaa.ui.adaptador.IngredienteAdaptador
import com.chema.practicapracticaa.ui.viewmodel.factory.DetalleRecetaViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetalleRecetaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleRecetaBinding
    private lateinit var viewModel: DetalleRecetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        establecerFunciones()
    }

    private fun establecerFunciones(){
        establecerViewModel()
        observarViewModel()
        atras()
    }

    private fun establecerViewModel(){
        val repositorio = RecetaRepositorio()
        val factory = DetalleRecetaViewModelFactory(repositorio)
        viewModel = ViewModelProvider(this, factory)[DetalleRecetaViewModel::class.java]
    }

    private fun observarViewModel(){
        val recetaId = intent.getStringExtra("RECIPE_ID") ?: ""

        viewModel.recetaDetalles.observe(this) { recipe ->
            binding.textViewRecipeName.text = recipe.strMeal
            Glide.with(this)
                .load(recipe.strMealThumb)
                .into(binding.imageViewRecipe)

            binding.textViewPreparation.text = recipe.strInstructions

            lifecycle.addObserver(binding.youtubePlayerView)
            binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(obtenerVideoId(recipe.strYoutube), 0f)
                }
            })

            configuracionRecyclerviewIngredientes(recipe)
        }

        viewModel.buscarDetallesDeReceta(recetaId)
    }

    private fun configuracionRecyclerviewIngredientes(receta: Receta) {
        val ingredientesConMedidas = receta.obtenerIngredientesConMedidas()
        val adaptador = IngredienteAdaptador(ingredientesConMedidas)

        binding.recyclerViewIngredients.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewIngredients.adapter = adaptador

        iniciarAutoScrollRecyclerView(binding.recyclerViewIngredients)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun iniciarAutoScrollRecyclerView(recyclerView: RecyclerView) {
        val velocidadScroll = 2000L
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                val nextPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1
                if (nextPosition >= recyclerView.adapter?.itemCount ?: 0) {
                    recyclerView.scrollToPosition(0)
                } else {
                    recyclerView.smoothScrollToPosition(nextPosition)
                }
                handler.postDelayed(this, velocidadScroll)
            }
        }
        handler.postDelayed(runnable, velocidadScroll)

        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                handler.removeCallbacks(runnable)
            } else if (event.action == MotionEvent.ACTION_UP) {
                handler.postDelayed(runnable, velocidadScroll)
            }
            false
        }
    }



    private fun obtenerVideoId(youtubeUrl: String?): String {
        return youtubeUrl?.substringAfter("v=") ?: ""
    }

    private fun atras(){
        binding.arrowBack.setOnClickListener {
            finish()
        }
    }
}