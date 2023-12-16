package io.github.hiiragi283.laboratory.common.block

import io.github.hiiragi283.laboratory.common.block.entity.HTMortarBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object HTMortarBlock : Block(FabricBlockSettings.copy(Blocks.TERRACOTTA)), BlockEntityProvider {

    //    AbstractBlock    //

    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient) {
            state.createScreenHandlerFactory(world, pos)?.let(player::openHandledScreen)
        }
        return ActionResult.SUCCESS
    }

    @Deprecated("Deprecated in Java")
    override fun onSyncedBlockEvent(state: BlockState, world: World, pos: BlockPos, type: Int, data: Int): Boolean {
        super.onSyncedBlockEvent(state, world, pos, type, data)
        return world.getBlockEntity(pos)?.onSyncedBlockEvent(type, data) ?: false
    }

    //    BlockEntityProvider    //

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = HTMortarBlockEntity(pos, state)

}