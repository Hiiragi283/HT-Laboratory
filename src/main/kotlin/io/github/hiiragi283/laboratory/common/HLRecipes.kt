package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipe
import io.github.hiiragi283.laboratory.api.recipe.HTRecipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry

object HLRecipes {

    private fun <T : HTRecipe<T>> serializer(name: String, serializer: RecipeSerializer<T>): RecipeSerializer<T> =
        Registry.register(Registry.RECIPE_SERIALIZER, HTLaboratoryCommon.id(name), serializer)

    //    Serializer    //

    @JvmField
    val MORTAR_SERIALIZER: RecipeSerializer<HTMortarRecipe> = serializer("mortar", HTMortarRecipe.Serializer)

    //    Type    //

    private fun <T : HTRecipe<T>> recipeType(name: String): RecipeType<T> = Registry.register(
        Registry.RECIPE_TYPE,
        HTLaboratoryCommon.id(name),
        object : RecipeType<T> {
            override fun toString(): String = name
        }
    )

    @JvmField
    val MORTAR_TYPE: RecipeType<HTMortarRecipe> = recipeType("mortar")

}