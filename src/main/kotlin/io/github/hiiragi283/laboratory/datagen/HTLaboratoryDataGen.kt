package io.github.hiiragi283.laboratory.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
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

		}

	}

}