package io.github.hiiragi283.laboratory.compat.rei

import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipe
import io.github.hiiragi283.laboratory.common.HLBlocks
import io.github.hiiragi283.laboratory.common.HLRecipeTypes
import io.github.hiiragi283.laboratory.common.HTLaboratoryCommon
import io.github.hiiragi283.laboratory.compat.rei.displays.HTMortarCategory
import io.github.hiiragi283.laboratory.compat.rei.displays.HTMortarDisplay
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.util.EntryStacks
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object HLReiPlugin : REIClientPlugin {

    @JvmField
    val MORTAR: CategoryIdentifier<HTMortarDisplay> = CategoryIdentifier.of(HTLaboratoryCommon.MOD_ID, "mortar")

    override fun registerCategories(registry: CategoryRegistry) {

        registry.add(HTMortarCategory)

        registry.addWorkstations(MORTAR, EntryStacks.of(HLBlocks.MORTAR))

    }

    override fun registerDisplaySerializer(registry: DisplaySerializerRegistry) {
        registry.register(MORTAR, BasicDisplay.Serializer.ofSimple(::HTMortarDisplay))
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        registry.registerRecipeFiller(HTMortarRecipe::class.java, HLRecipeTypes.MORTAR, ::HTMortarDisplay)
    }

    override fun registerScreens(registry: ScreenRegistry) {
        //registry.registerContainerClickArea(Rectangle(68, 49, 22, 16), HTMortarScreen::class.java, MORTAR)
    }

}