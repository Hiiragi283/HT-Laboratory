package io.github.hiiragi283.laboratory.client

import io.github.hiiragi283.laboratory.client.gui.HTMortarScreen
import io.github.hiiragi283.laboratory.common.HLScreenHandlerTypes
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.HandledScreens

@Environment(EnvType.CLIENT)
object HTLaboratoryClient : ClientModInitializer {

    override fun onInitializeClient() {

        HandledScreens.register(HLScreenHandlerTypes.MORTAR, ::HTMortarScreen)

    }

}