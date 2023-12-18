package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.common.screen.HTMortarScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.registry.Registry

object HLScreenHandlerTypes {

    private fun <T : ScreenHandler> register(
        path: String,
        factory: ScreenHandlerType.Factory<T>
    ): ScreenHandlerType<T> = Registry.register(
        Registry.SCREEN_HANDLER,
        HTLaboratoryCommon.id(path),
        ScreenHandlerType(factory)
    )

    //    Process    //

    @JvmField
    val MORTAR: ScreenHandlerType<HTMortarScreenHandler> = register("mortar", ::HTMortarScreenHandler)

}