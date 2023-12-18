package io.github.hiiragi283.laboratory.datagen

import io.github.hiiragi283.laboratory.common.HLBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.data.server.BlockLootTableGenerator

class HLBlockLootTableProvider(fabricDataGenerator: FabricDataGenerator) :
    FabricBlockLootTableProvider(fabricDataGenerator) {

    override fun generateBlockLootTables() {
        addDrop(HLBlocks.MORTAR, BlockLootTableGenerator::nameableContainerDrops)
        addDrop(HLBlocks.PORCELAIN)
    }

}