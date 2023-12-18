package io.github.hiiragi283.laboratory.common

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry

object HLBlockEntityTypes {

    private fun <T : BlockEntity> register(path: String, builder: FabricBlockEntityTypeBuilder<T>): BlockEntityType<T> =
        Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            HTLaboratoryCommon.id(path),
            builder.build()
        )

    //    Process    //

    /*@JvmField
    val MORTAR: BlockEntityType<HTMortarBlockEntity> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        HTLaboratoryCommon.id("mortar"),
        FabricBlockEntityTypeBuilder
            .create(::HTMortarBlockEntity)
            .addBlock(HLBlocks.MORTAR)
            .build()
    )*/

}