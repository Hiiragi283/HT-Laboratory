package io.github.hiiragi283.laboratory.common.block

import io.github.hiiragi283.laboratory.common.screen.HTMortarScreenHandler
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.CraftingTableBlock
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
import net.minecraft.world.World

object HTMortarBlock : CraftingTableBlock(FabricBlockSettings.copy(Blocks.TERRACOTTA)) {

    private val TITLE: Text = TranslatableText("container.ht_laboratory.mortar")

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
    override fun createScreenHandlerFactory(
        state: BlockState,
        world: World,
        pos: BlockPos
    ): NamedScreenHandlerFactory = SimpleNamedScreenHandlerFactory({ syncId: Int, inventory: PlayerInventory, _ ->
        HTMortarScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos))
    }, TITLE)

}