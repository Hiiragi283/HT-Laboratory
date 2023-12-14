package io.github.hiiragi283.laboratory.datagen

import io.github.hiiragi283.laboratory.api.recipe.HTIngredient
import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipeJsonBuilder
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Items
import java.util.function.Consumer

object HTLaboratoryDataGen : DataGeneratorEntrypoint {

	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		fabricDataGenerator.addProvider(::TestTagProvider)
		fabricDataGenerator.addProvider(::TestRecipeProvider)
	}

	class TestTagProvider(dataGenerator: FabricDataGenerator) : ItemTagProvider(dataGenerator) {

		override fun generateTags() {

		}

	}

	class TestRecipeProvider(dataGenerator: FabricDataGenerator) : FabricRecipeProvider(dataGenerator) {

		override fun generateRecipes(exporter: Consumer<RecipeJsonProvider>) {
			HTMortarRecipeJsonBuilder(
				HTIngredient.ofItem(Items.BONE_MEAL, 6),
				HTIngredient.ofItem(Items.BONE, 1)
			).criterion("has_bone", conditionsFromItem(Items.BONE)).offerTo(exporter)
		}

	}

}