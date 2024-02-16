package io.github.hiiragi283.laboratory.compat

import io.github.hiiragi283.laboratory.api.recipe.HTIngredient
import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipeJsonBuilder
import io.github.hiiragi283.laboratory.common.HTLaboratoryCommon
import io.github.hiiragi283.material.HTRecipeManager
import io.github.hiiragi283.material.api.HTMaterialsAddon
import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.HTMaterialKey
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

object HLHTAddon : HTMaterialsAddon {

    override val modId: String = HTLaboratoryCommon.MOD_ID

    override val priority: Int = 0

    override fun commonSetup() {
        HTMaterial.REGISTRY.forEach { (key: HTMaterialKey, material: HTMaterial) ->
            mortar(key, material)
        }
    }

    private fun mortar(key: HTMaterialKey, material: HTMaterial) {
        //1x XX Ingot/Gem -> 1x XX Dust
        fun dustRecipe(material: HTMaterial) {
            val tagKey: TagKey<Item> = material.getDefaultShape()!!.getCommonTag(key)
            val output: Item = HTPartManager.getDefaultItem(key, HTShapes.DUST) ?: return
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