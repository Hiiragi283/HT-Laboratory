package io.github.hiiragi283.laboratory.compat

import io.github.hiiragi283.laboratory.api.recipe.HTIngredient
import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipeJsonBuilder
import io.github.hiiragi283.laboratory.common.HTLaboratoryCommon
import io.github.hiiragi283.material.api.addon.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes
import io.github.hiiragi283.material.common.HTRecipeManager
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

object HLHTAddon : HTMaterialsAddon {

    override val modId: String = HTLaboratoryCommon.MOD_ID

    override fun commonSetup() {
        HTMaterial.REGISTRY.forEach { material ->
            mortar(material)
        }
    }

    private fun mortar(material: HTMaterial) {
        //1x XX Ingot/Gem -> 1x XX Dust
        fun dustRecipe(material: HTMaterial) {
            val tagKey: TagKey<Item> = material.getDefaultShape()!!.getCommonTag(material)
            val output: Item = HTPartManager.getDefaultItem(material, HTShapes.DUST) ?: return
            HTLaboratoryCommon.LOGGER.info("Output Id: ${Registry.ITEM.getId(output)}")
            HTRecipeManager.registerVanillaRecipe(
                HTLaboratoryCommon.id("mortar/material/${material}_dust"),
                HTMortarRecipeJsonBuilder(output)
                    .setPrimaryInput(HTIngredient.ofTag(tagKey))
                    .criterion("has_${material}", FabricRecipeProvider.conditionsFromTag(tagKey))
            )
        }
        when (material.getDefaultShape()) {
            HTShapes.INGOT -> dustRecipe(material)
            HTShapes.GEM -> dustRecipe(material)
            else -> Unit
        }
    }

}