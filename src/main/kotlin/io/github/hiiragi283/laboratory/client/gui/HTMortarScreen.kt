package io.github.hiiragi283.laboratory.client.gui

import com.mojang.blaze3d.systems.RenderSystem
import io.github.hiiragi283.laboratory.common.screen.HTMortarScreenHandler
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.ForgingScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

private val TEXTURE = Identifier("textures/gui/container/smithing.png")

@Environment(EnvType.CLIENT)
class HTMortarScreen(
    handler: HTMortarScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : ForgingScreen<HTMortarScreenHandler>(handler, playerInventory, title, TEXTURE) {

    init {
        titleX = 60
        titleY = 18
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        RenderSystem.disableBlend()
        super.drawForeground(matrices, mouseX, mouseY)
    }

}