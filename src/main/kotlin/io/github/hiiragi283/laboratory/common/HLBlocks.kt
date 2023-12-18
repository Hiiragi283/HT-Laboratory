package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.common.block.HTMortarBlock
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.util.registry.Registry

object HLBlocks {

    private val map: MutableMap<String, Block> = mutableMapOf()

    @JvmField
    val REGISTRY: Map<String, Block> = map

    private fun register(path: String, block: Block): Block {
        map.putIfAbsent(path, block)
        return Registry.register(Registry.BLOCK, HTLaboratoryCommon.id(path), block)
    }

    private inline fun register(path: String, block: Block, blockItem: (Block) -> BlockItem): Block {
        HLItems.register(path, blockItem(block))
        return register(path, block)
    }

    //    Process    //

    @JvmField
    val MORTAR: Block = register("mortar", HTMortarBlock) {
        BlockItem(
            it,
            FabricItemSettings().group(ItemGroup.DECORATIONS)
        )
    }

    //    Decoration    //

    @JvmField
    val PORCELAIN: Block = register("porcelain", Block(FabricBlockSettings.copy(Blocks.TERRACOTTA))) {
        BlockItem(it, FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS))
    }

}