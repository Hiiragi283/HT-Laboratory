package io.github.hiiragi283.laboratory.common.block

import io.github.hiiragi283.laboratory.common.screen.HTMortarScreenHandler
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.CraftingTableBlock
import net.minecraft.block.ShapeContext
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

@Suppress("OVERRIDE_DEPRECATION")
object HTMortarBlock : CraftingTableBlock(
    FabricBlockSettings.copy(Blocks.TERRACOTTA).breakInstantly().nonOpaque()
) {

    const val ID = "mortar"

    @JvmStatic
    fun getTitle(): Text = TranslatableText(translationKey)

    private val SHAPE = createCuboidShape(3.0, 0.0, 3.0, 13.0, 4.0, 13.0)

    //    AbstractBlock    //

    override fun canPathfindThrough(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        type: NavigationType
    ): Boolean = false

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

    override fun createScreenHandlerFactory(
        state: BlockState,
        world: World,
        pos: BlockPos
    ): NamedScreenHandlerFactory = SimpleNamedScreenHandlerFactory({ syncId: Int, inventory: PlayerInventory, _ ->
        HTMortarScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos))
    }, getTitle())

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape = SHAPE

}