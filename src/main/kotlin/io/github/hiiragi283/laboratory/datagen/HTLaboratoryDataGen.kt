package io.github.hiiragi283.laboratory.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider

object HTLaboratoryDataGen : DataGeneratorEntrypoint {

	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		fabricDataGenerator.addProvider(::TestTagProvider)
	}

	class TestTagProvider(dataGenerator: FabricDataGenerator) : ItemTagProvider(dataGenerator) {

		override fun generateTags() {

		}

	}

}