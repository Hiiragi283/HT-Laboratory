package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry

sealed class HTIngredient {

    abstract val count: Int

    abstract val matchingStacks: Collection<ItemStack>

    abstract fun matches(stack: ItemStack): Boolean

    abstract fun toJson(): JsonObject

    companion object {

        private val EMPTY: HTIngredient = ofItem(Items.AIR)

        @JvmStatic
        fun empty(): HTIngredient = EMPTY

        @JvmStatic
        fun ofItem(pair: Pair<net.minecraft.item.Item, Int>) = ofItem(pair.first, pair.second)

        @JvmOverloads
        @JvmStatic
        fun ofItem(item: net.minecraft.item.Item, count: Int = 1) = Item(item, count)

        @JvmStatic
        fun ofTag(pair: Pair<TagKey<net.minecraft.item.Item>, Int>) = ofTag(pair.first, pair.second)

        @JvmOverloads
        @JvmStatic
        fun ofTag(tagKey: TagKey<net.minecraft.item.Item>, count: Int = 1) = Tag(tagKey, count)

    }

    class Item(
        val item: net.minecraft.item.Item,
        override val count: Int
    ) : HTIngredient() {

        override val matchingStacks: Collection<ItemStack> = listOf(item.defaultStack)

        override fun matches(stack: ItemStack): Boolean = stack.isOf(item) && stack.count >= count

        override fun toJson(): JsonObject {
            val jsonObject = JsonObject()
            jsonObject.addProperty("item", Registry.ITEM.getId(item).toString())
            jsonObject.addProperty("count", count)
            return jsonObject
        }

        operator fun component1(): net.minecraft.item.Item = item

        operator fun component2(): Int = count

    }

    class Tag(
        val tagKey: TagKey<net.minecraft.item.Item>,
        override val count: Int
    ) : HTIngredient() {

        override val matchingStacks: Collection<ItemStack> = Registry.ITEM.getOrCreateEntryList(tagKey)
            .map(RegistryEntry<net.minecraft.item.Item>::value)
            .map(net.minecraft.item.Item::getDefaultStack)

        override fun matches(stack: ItemStack): Boolean = stack.isIn(tagKey) && stack.count >= count

        override fun toJson(): JsonObject {
            val jsonObject = JsonObject()
            jsonObject.addProperty("tag", tagKey.id.toString())
            jsonObject.addProperty("count", count)
            return jsonObject
        }

        operator fun component1(): TagKey<net.minecraft.item.Item> = tagKey

        operator fun component2(): Int = count

    }

}