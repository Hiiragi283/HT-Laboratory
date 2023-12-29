package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipe
import io.github.hiiragi283.laboratory.common.block.HTMortarBlock
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry

object HLRecipeTypes {

    private class Impl<T : Recipe<*>>(val name: String) : RecipeType<T> {
        override fun toString(): String = name
    }

    private fun <T : Recipe<*>> recipeType(name: String): RecipeType<T> = Registry.register(
        Registry.RECIPE_TYPE,
        HTLaboratoryCommon.id(name),
        Impl(name)
    )

    @JvmField
    val MORTAR: RecipeType<HTMortarRecipe> = recipeType(HTMortarBlock.ID)

}