package io.github.hiiragi283.laboratory.common

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

}