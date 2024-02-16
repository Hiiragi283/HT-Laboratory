package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipe
import io.github.hiiragi283.laboratory.common.block.HTMortarBlock
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.registry.Registry

object HLRecipeSerializers {

    private fun <T : Recipe<*>> serializer(name: String, serializer: RecipeSerializer<T>): RecipeSerializer<T> =
        Registry.register(Registry.RECIPE_SERIALIZER, HTLaboratoryCommon.id(name), serializer)

    @JvmField
    val MORTAR_SERIALIZER: RecipeSerializer<HTMortarRecipe> = serializer(HTMortarBlock.ID, HTMortarRecipe.Serializer)

}