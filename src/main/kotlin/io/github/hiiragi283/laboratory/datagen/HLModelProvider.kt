package io.github.hiiragi283.laboratory.datagen

import io.github.hiiragi283.laboratory.common.HLBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator

class HLModelProvider(fabricDataGenerator: FabricDataGenerator) : FabricModelProvider(fabricDataGenerator) {

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(HLBlocks.MORTAR)
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {

    }

}