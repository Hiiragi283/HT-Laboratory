package io.github.hiiragi283.laboratory.common.block

import io.github.hiiragi283.laboratory.common.block.entity.HTAshCollectorBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

@Suppress("OVERRIDE_DEPRECATION")
object HTAshCollectorBlock : Block(
    FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.5f)
), BlockEntityProvider {

    const val ID = "ash_collector"

    /*override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (world.isClient) return ActionResult.SUCCESS
        (world.getBlockEntity(pos) as? HTAshCollectorBlockEntity)?.run {
            player.openHandledScreen(this)
        }
        return ActionResult.CONSUME
    }*/

    override fun hasComparatorOutput(state: BlockState): Boolean = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int =
        ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    //    BlockEntityProvider    //

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        HTAshCollectorBlockEntity(pos, state)

}