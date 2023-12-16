package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.common.block.entity.HTMortarBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry

object HLBlockEntityTypes {

    //    Process    //

    @JvmField
    val MORTAR: BlockEntityType<HTMortarBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        HTLaboratoryCommon.id("mortar"),
        FabricBlockEntityTypeBuilder
            .create(::HTMortarBlockEntity)
            .addBlock(HLBlocks.MORTAR)
            .build()
    )

}