package com.chema.practicapracticaa.modelo

data class Receta(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strMealThumb: String,
    val strInstructions: String,
    val strYoutube: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure20: String?,
){
    fun obtenerIngredientesConMedidas(): List<Pair<String, String>> {
        val ingredientsWithMeasures = mutableListOf<Pair<String, String>>()
        for (i in 1..20) {
            val ingredient = this::class.java.getDeclaredField("strIngredient$i").get(this) as? String
            val measure = this::class.java.getDeclaredField("strMeasure$i").get(this) as? String
            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                ingredientsWithMeasures.add(Pair(ingredient, measure))
            }
        }
        return ingredientsWithMeasures
    }
}
