package io.github.hiiragi283.laboratory.client.gui

import io.github.hiiragi283.laboratory.common.screen.HTAshCollectorScreenHandler
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

private val TEXTURE = Identifier("textures/gui/container/smithing.png")

@Environment(EnvType.CLIENT)
class HTAshCollectorScreen(
    handler: HTAshCollectorScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : HandledScreen<HTAshCollectorScreenHandler>(handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {

    }

}