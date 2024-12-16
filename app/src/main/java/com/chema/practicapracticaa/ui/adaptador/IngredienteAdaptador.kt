package com.chema.practicapracticaa.ui.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chema.practicapracticaa.databinding.ItemIngredienteBinding

class IngredienteAdaptador(private val ingredientes: List<Pair<String, String>>) :
    RecyclerView.Adapter<IngredienteAdaptador.IngredientViewHolder>() {

    inner class IngredientViewHolder(private val binding: ItemIngredienteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Pair<String, String>) {
            binding.textViewIngredientName.text = ingredient.first
            binding.textViewMeasure.text = ingredient.second
            Glide.with(binding.root.context)
                .load("https://www.themealdb.com/images/ingredients/${ingredient.first}.png")
                .into(binding.imageViewIngredient)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredientes[position])
    }

    override fun getItemCount(): Int = ingredientes.size
}
