package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.common.block.HTMortarBlock
import net.minecraft.block.Block
import net.minecraft.util.registry.Registry

object HLBlocks {

    //    Process    //

    @JvmField
    val MORTAR: Block = Registry.register(Registry.BLOCK, HTLaboratoryCommon.id("mortar"), HTMortarBlock)


}