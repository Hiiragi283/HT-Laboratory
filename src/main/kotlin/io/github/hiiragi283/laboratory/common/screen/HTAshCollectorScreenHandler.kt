package io.github.hiiragi283.laboratory.common.screen

import io.github.hiiragi283.laboratory.common.HLScreenHandlerTypes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext

class HTAshCollectorScreenHandler(
    syncId: Int,
    val playerInventory: PlayerInventory,
    val context: ScreenHandlerContext = ScreenHandlerContext.EMPTY
) : ScreenHandler(HLScreenHandlerTypes.ASH_COLLECTOR, syncId) {

    override fun canUse(player: PlayerEntity): Boolean = true

}