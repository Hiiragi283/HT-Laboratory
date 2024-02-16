package io.github.hiiragi283.laboratory.api.recipe

import net.minecraft.advancement.Advancement
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.util.Identifier

interface HTCraftingRecipeJsonBuilder : CraftingRecipeJsonBuilder {

    val advancementBuilder: Advancement.Builder

    val count: Int

    fun validate(recipeId: Identifier) {
        check(advancementBuilder.criteria.isNotEmpty()) { "No way of obtaining the recipe: $recipeId" }
        check(count > 0) { "Output count of the recipe: $recipeId must be natural number" }
    }

}