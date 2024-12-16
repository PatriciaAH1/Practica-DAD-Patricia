package com.chema.practicapracticaa.ui.adaptador

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chema.practicapracticaa.databinding.ItemRecetaBinding
import com.chema.practicapracticaa.modelo.Receta

class RecetaAdaptador(
    private val isLoggedIn: Boolean,
    private val onRecetaClick: (String) -> Unit,
    private val onInfoClick: () -> Unit,
    private val onImageClick: (String) -> Unit
) : ListAdapter<Receta, RecetaAdaptador.RecipeViewHolder>(RecipeDiffCallback()) {

    inner class RecipeViewHolder(private val binding: ItemRecetaBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(receta: Receta) {
            binding.textViewName.text = receta.strMeal
            binding.textViewCategory.text = "Categoría: ${receta.strCategory}"
            binding.textViewArea.text = "Comida de: ${receta.strArea}"

            Glide.with(binding.root.context)
                .load(receta.strMealThumb)
                .into(binding.roundedImageViewRecipe)

            binding.root.setOnClickListener {
                if (isLoggedIn) {
                    onRecetaClick(receta.idMeal)
                } else {
                    onInfoClick()
                }
            }

            binding.roundedImageViewRecipe.setOnClickListener {
                onImageClick(receta.strMealThumb)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<Receta>() {
        override fun areItemsTheSame(oldItem: Receta, newItem: Receta): Boolean = oldItem.idMeal == newItem.idMeal
        override fun areContentsTheSame(oldItem: Receta, newItem: Receta): Boolean = oldItem == newItem
    }
}
