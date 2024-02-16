package io.github.hiiragi283.laboratory.common

import io.github.hiiragi283.laboratory.common.item.HTBeakerItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object HLItems {

    private val map: MutableMap<String, Item> = mutableMapOf()

    @JvmField
    val REGISTRY: Map<String, Item> = map

    internal fun register(path: String, item: Item): Item {
        map.putIfAbsent(path, item)
        return Registry.register(Registry.ITEM, HTLaboratoryCommon.id(path), item)
    }

    //    Process    //

    //    Ingredient    //

    @JvmField
    val BEAKER: Item = register("beaker", HTBeakerItem)

}