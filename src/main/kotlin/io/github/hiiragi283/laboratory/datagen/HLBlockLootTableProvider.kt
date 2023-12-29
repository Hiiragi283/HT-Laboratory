package io.github.hiiragi283.laboratory.datagen

import io.github.hiiragi283.laboratory.common.HLBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider

class HLBlockLootTableProvider(fabricDataGenerator: FabricDataGenerator) :
    FabricBlockLootTableProvider(fabricDataGenerator) {

    override fun generateBlockLootTables() {
        addDrop(HLBlocks.ASH_COLLECTOR)
        addDrop(HLBlocks.MORTAR)
        addDrop(HLBlocks.PORCELAIN)
    }

}