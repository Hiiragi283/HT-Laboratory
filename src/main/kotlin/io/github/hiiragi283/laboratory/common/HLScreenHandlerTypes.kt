package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.common.screen.HTMortarScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.registry.Registry

object HLScreenHandlerTypes {

    //    Process    //

    @JvmField
    val MORTAR: ScreenHandlerType<HTMortarScreenHandler> = Registry.register(
        Registry.SCREEN_HANDLER,
        HTLaboratoryCommon.id("mortar"),
        ScreenHandlerType(::HTMortarScreenHandler)
    )

}