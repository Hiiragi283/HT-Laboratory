package io.github.hiiragi283.laboratory.datagen

import io.github.hiiragi283.laboratory.api.recipe.HTIngredient
import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipeJsonBuilder
import io.github.hiiragi283.laboratory.common.HTLaboratoryCommon
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Items
import java.util.function.Consumer

class HLRecipeProvider(dataGenerator: FabricDataGenerator) : FabricRecipeProvider(dataGenerator) {

    override fun generateRecipes(exporter: Consumer<RecipeJsonProvider>) {
        HTMortarRecipeJsonBuilder(Items.BONE_MEAL, 6)
            .setPrimaryInput(HTIngredient.ofItem(Items.BONE, 1))
            .criterion("has_bone", conditionsFromItem(Items.BONE))
            .offerTo(exporter, HTLaboratoryCommon.id("mortar/bone_meal"))
    }

}