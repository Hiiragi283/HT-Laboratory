package io.github.hiiragi283.laboratory.datagen

import io.github.hiiragi283.laboratory.api.recipe.HTIngredient
import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipeJsonBuilder
import io.github.hiiragi283.laboratory.common.HLBlocks
import io.github.hiiragi283.laboratory.common.HTLaboratoryCommon
import io.github.hiiragi283.material.api.material.materials.HTCommonMaterials
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import java.util.function.Consumer

class HLRecipeProvider(fabricDataGenerator: FabricDataGenerator) : FabricRecipeProvider(fabricDataGenerator) {

    override fun generateRecipes(exporter: Consumer<RecipeJsonProvider>) {
        crafting(exporter)
        blastFurnace(exporter)
        mortar(exporter)
    }

    private fun crafting(exporter: Consumer<RecipeJsonProvider>) {
        //5x Porcelain Block -> 1x Mortar
        ShapedRecipeJsonBuilder.create(HLBlocks.MORTAR)
            .pattern("A A")
            .pattern("AAA")
            .input('A', Ingredient.ofItems(HLBlocks.PORCELAIN))
            .criterion("has_porcelain", conditionsFromItem(HLBlocks.PORCELAIN))
            .offerTo(exporter, HTLaboratoryCommon.id("shaped/mortar"))
    }

    private fun blastFurnace(exporter: Consumer<RecipeJsonProvider>) {
        //1x Clay Block -> 1x Porcelain Block
        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Blocks.CLAY), HLBlocks.PORCELAIN, 0.0f, 100)
            .criterion("has_clay", conditionsFromItem(Blocks.CLAY))
            .offerTo(exporter, HTLaboratoryCommon.id("blasting/porcelain"))
    }

    private fun mortar(exporter: Consumer<RecipeJsonProvider>) {
        //1x Bone -> 6x Bone Meal
        HTMortarRecipeJsonBuilder(Items.BONE_MEAL, 6)
            .setPrimaryInput(HTIngredient.ofItem(Items.BONE, 1))
            .criterion("has_bone", conditionsFromItem(Items.BONE))
            .offerTo(exporter, HTLaboratoryCommon.id("mortar/bone_meal"))
        //3x Copper Dust + 1x Tin Dust -> 4x Bronze Dust
        HTMortarRecipeJsonBuilder(HTPartManager.getDefaultItem(HTCommonMaterials.BRONZE, HTShapes.DUST)!!, 4)
            .setPrimaryInput(HTIngredient.ofTag(HTShapes.DUST.getCommonTag(HTElementMaterials.COPPER), 3))
            .setSecondaryInput(HTIngredient.ofTag(HTShapes.DUST.getCommonTag(HTElementMaterials.TIN), 1))
            .criterion("has_copper_dust", conditionsFromTag(HTShapes.DUST.getCommonTag(HTElementMaterials.COPPER)))
            .criterion("has_tin_dust", conditionsFromTag(HTShapes.DUST.getCommonTag(HTElementMaterials.TIN)))
            .offerTo(exporter, HTLaboratoryCommon.id("mortar/mix/bronze_dust"))
    }

}