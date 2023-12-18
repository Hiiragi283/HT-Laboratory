package io.github.hiiragi283.laboratory.compat.rei

import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object HLReiPlugin : REIClientPlugin {

    override fun registerCategories(registry: CategoryRegistry) {

    }

    override fun registerDisplays(registry: DisplayRegistry) {

    }

}