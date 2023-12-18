package io.github.hiiragi283.laboratory.client

import io.github.hiiragi283.laboratory.client.gui.HTMortarScreen
import io.github.hiiragi283.laboratory.common.HLBlocks
import io.github.hiiragi283.laboratory.common.HLScreenHandlerTypes
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.client.render.RenderLayer

@Environment(EnvType.CLIENT)
object HTLaboratoryClient : ClientModInitializer {

    override fun onInitializeClient() {

        HandledScreens.register(HLScreenHandlerTypes.MORTAR, ::HTMortarScreen)

        BlockRenderLayerMap.INSTANCE.putBlock(HLBlocks.MORTAR, RenderLayer.getCutout())

    }

}