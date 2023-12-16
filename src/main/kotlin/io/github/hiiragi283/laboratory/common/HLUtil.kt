@file:JvmName("HLUtil")

package io.github.hiiragi283.laboratory.common

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.Registry

//    ItemStack    //

fun ItemStack.toJson(): JsonObject = JsonObject().apply {
    addProperty("item", Registry.ITEM.getId(this@toJson.item).toString())
    addProperty("count", this@toJson.count)
}