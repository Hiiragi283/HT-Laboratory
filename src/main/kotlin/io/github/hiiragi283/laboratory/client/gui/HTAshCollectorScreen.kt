package io.github.hiiragi283.laboratory.client.gui

import com.mojang.blaze3d.systems.RenderSystem
import io.github.hiiragi283.laboratory.common.HTLaboratoryCommon
import io.github.hiiragi283.laboratory.common.block.HTAshCollectorBlock
import io.github.hiiragi283.laboratory.common.screen.HTAshCollectorScreenHandler
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

private val TEXTURE = HTLaboratoryCommon.id("textures/gui/${HTAshCollectorBlock.ID}.png")

@Environment(EnvType.CLIENT)
class HTAshCollectorScreen(
    handler: HTAshCollectorScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : HandledScreen<HTAshCollectorScreenHandler>(handler, playerInventory, title) {

    init {
        passEvents = false
        backgroundHeight = 133
        playerInventoryTitleY = backgroundHeight - 94
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val i = (this.width - this.backgroundWidth) / 2
        val j = (this.height - this.backgroundHeight) / 2
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight)
    }

}