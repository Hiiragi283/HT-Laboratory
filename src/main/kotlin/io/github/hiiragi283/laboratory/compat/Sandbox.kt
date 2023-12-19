package io.github.hiiragi283.laboratory.compat

import io.github.hiiragi283.material.api.material.HTMaterial
import io.github.hiiragi283.material.api.material.materials.HTElementMaterials
import io.github.hiiragi283.material.api.material.materials.HTVanillaMaterials
import io.github.hiiragi283.material.api.shape.HTShape
import io.github.hiiragi283.material.common.HTMaterialsCommon
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.Items

object Sandbox {

    private val MATERIAL_ITEM: HTMaterialApiLookup<Item, HTShape> =
        HTMaterialApiLookup.get(HTMaterialsCommon.id("material_item"), Item::class.java, HTShape::class.java)

    private val MATERIAL_FLUID: HTMaterialApiLookup<Fluid, Void?> =
        HTMaterialApiLookup.get(HTMaterialsCommon.id("material_fluid"), Fluid::class.java, Void::class.java)

    init {
        MATERIAL_ITEM.register({ _: HTMaterial, _: HTShape -> Items.AIR }, HTElementMaterials.IRON)
        MATERIAL_FLUID.register({ _: HTMaterial, _: Void? -> Fluids.WATER }, HTVanillaMaterials.WATER)
    }

    fun findItem(material: HTMaterial, shape: HTShape): Item? = MATERIAL_ITEM.find(material, shape)

    fun findFluid(material: HTMaterial) = MATERIAL_FLUID.find(material, null)

}