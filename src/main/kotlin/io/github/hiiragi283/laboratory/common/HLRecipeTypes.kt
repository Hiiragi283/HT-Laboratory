package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipe
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry

object HLRecipeTypes {

    //    Process    //

    @JvmField
    val MORTAR: RecipeType<HTMortarRecipe> = recipeType("mortar")

    private fun <T : Recipe<*>> recipeType(name: String): RecipeType<T> = Registry.register(
        Registry.RECIPE_TYPE,
        HTLaboratoryCommon.id(name),
        object : RecipeType<T> {
            override fun toString(): String = name
        }
    )

}