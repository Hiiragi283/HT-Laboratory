package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.common.block.HTAshCollectorBlock
import io.github.hiiragi283.laboratory.common.block.HTMortarBlock
import io.github.hiiragi283.laboratory.common.screen.HTAshCollectorScreenHandler
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
    val ASH_COLLECTOR: ScreenHandlerType<HTAshCollectorScreenHandler> =
        register(HTAshCollectorBlock.ID, ::HTAshCollectorScreenHandler)

    @JvmField
    val MORTAR: ScreenHandlerType<HTMortarScreenHandler> =
        register(HTMortarBlock.ID, ::HTMortarScreenHandler)

}