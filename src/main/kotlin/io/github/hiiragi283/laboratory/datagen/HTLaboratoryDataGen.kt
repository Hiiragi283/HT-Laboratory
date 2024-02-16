package io.github.hiiragi283.laboratory.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object HTLaboratoryDataGen : DataGeneratorEntrypoint {

	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		fabricDataGenerator.addProvider(::HLModelProvider)
		fabricDataGenerator.addProvider(::HLRecipeProvider)
		fabricDataGenerator.addProvider(::HLBlockLootTableProvider)
	}

}