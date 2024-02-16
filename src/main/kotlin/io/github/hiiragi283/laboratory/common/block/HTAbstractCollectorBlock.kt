package io.github.hiiragi283.laboratory.common.block

import io.github.hiiragi283.laboratory.api.block.HTBlockWithEntity
import io.github.hiiragi283.laboratory.common.block.entity.HTAshCollectorBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ItemScatterer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

@Suppress("OVERRIDE_DEPRECATION")
object HTAbstractCollectorBlock : HTBlockWithEntity(
    FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.5f)
) {

    @JvmStatic
    fun getTitle(): Text = TranslatableText(translationKey)

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block !== newState.block) {
            (world.getBlockEntity(pos) as? HTAshCollectorBlockEntity)?.run {
                ItemScatterer.spawn(world, pos, this)
                world.updateComparators(pos, this@HTAbstractCollectorBlock)
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    //    BlockEntityProvider    //

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        HTAshCollectorBlockEntity(pos, state)

}