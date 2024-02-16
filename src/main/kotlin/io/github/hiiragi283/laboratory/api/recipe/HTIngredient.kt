package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import io.github.hiiragi283.laboratory.common.HLItems
import io.github.hiiragi283.laboratory.common.item.HTBeakerItem
import io.github.hiiragi283.material.util.getEntries
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Consumer
import java.util.function.Predicate

sealed class HTIngredient(val count: Int) : Predicate<ItemStack> {

    abstract fun getMatchingStacks(): Collection<ItemStack>

    abstract fun jsonConsumer(consumer: Consumer<JsonObject>)

    fun write(buf: PacketByteBuf) {
        buf.writeCollection(getMatchingStacks(), PacketByteBuf::writeItemStack)
    }

    companion object {

        @JvmStatic
        fun empty(): HTIngredient = Empty

        @JvmOverloads
        @JvmStatic
        fun ofItem(item: ItemConvertible, count: Int = 1): HTIngredient = Item(item.asItem(), count)

        @JvmOverloads
        @JvmStatic
        fun ofTag(tagKey: TagKey<net.minecraft.item.Item>, count: Int = 1): HTIngredient = Tag(tagKey, count)

        @JvmOverloads
        @JvmStatic
        fun ofFluid(fluid: net.minecraft.fluid.Fluid, count: Int = 1): HTIngredient = Fluid(fluid, count)

        @JvmStatic
        fun read(json: JsonElement): HTIngredient {
            if (json.isJsonObject) {
                val jsonObject: JsonObject = json.asJsonObject
                val hasItem: Boolean = jsonObject.has("item")
                val hasTag: Boolean = jsonObject.has("tag")
                val hasFluid: Boolean = jsonObject.has("fluid")
                val hasCount: Boolean = jsonObject.has("count")
                when {
                    hasItem && hasTag && hasFluid || hasItem && hasTag || hasItem && hasFluid || hasTag && hasFluid ->
                        throw JsonParseException("")

                    hasItem -> {
                        val itemId = Identifier(jsonObject.getAsJsonPrimitive("item").asString)
                        val item: net.minecraft.item.Item = Registry.ITEM.get(itemId)
                        val count: Int = if (hasCount) jsonObject.get("count").asInt else 1
                        return Item(item, count)
                    }

                    hasTag -> {
                        val tagId = Identifier(jsonObject.getAsJsonPrimitive("tag").asString)
                        val tagKey: TagKey<net.minecraft.item.Item> = TagKey.of(Registry.ITEM_KEY, tagId)
                        val count: Int = if (hasCount) jsonObject.get("count").asInt else 1
                        return Tag(tagKey, count)
                    }

                    hasFluid -> {
                        val fluidId = Identifier(jsonObject.getAsJsonPrimitive("fluid").asString)
                        val fluid: net.minecraft.fluid.Fluid = Registry.FLUID.get(fluidId)
                        val count: Int = if (hasCount) jsonObject.get("count").asInt else 1
                        return Fluid(fluid, count)
                    }

                    else -> throw JsonParseException("An ingredient entry needs any of a tag, a fluid or an item!")
                }
            } else throw JsonSyntaxException("HTIngredient must be JsonObject!")
        }

        @JvmStatic
        fun read(buf: PacketByteBuf): HTIngredient {
            val list: List<ItemStack> = buf.readList(PacketByteBuf::readItemStack)
            return if (list.isEmpty()) empty() else Packet(list)
        }

    }

    data object Empty : HTIngredient(0) {

        override fun getMatchingStacks(): Collection<ItemStack> = listOf()

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {

        }

        override fun test(stack: ItemStack): Boolean = stack.isEmpty

    }

    private class Item(
        private val item: net.minecraft.item.Item,
        count: Int
    ) : HTIngredient(count) {

        override fun getMatchingStacks(): Collection<ItemStack> = listOf(ItemStack(item, count))

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {
            consumer.accept(JsonObject().apply {
                addProperty("item", Registry.ITEM.getId(item).toString())
                addProperty("count", count)
            })
        }

        override fun test(stack: ItemStack): Boolean = stack.isOf(item) && stack.count >= count

    }

    private class Tag(
        private val tagKey: TagKey<net.minecraft.item.Item>,
        count: Int
    ) : HTIngredient(count) {

        override fun getMatchingStacks(): Collection<ItemStack> =
            tagKey.getEntries(Registry.ITEM).map { ItemStack(it, count) }

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {
            consumer.accept(JsonObject().apply {
                addProperty("tag", tagKey.id.toString())
                addProperty("count", count)
            })
        }

        override fun test(stack: ItemStack): Boolean = stack.isIn(tagKey) && stack.count >= count

    }

    private class Fluid(private val fluid: net.minecraft.fluid.Fluid, count: Int) : HTIngredient(count) {

        override fun getMatchingStacks(): Collection<ItemStack> = listOf(HTBeakerItem.getStack(fluid, count))

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {
            consumer.accept(JsonObject().apply {
                addProperty("fluid", Registry.FLUID.getId(fluid).toString())
                addProperty("count", count)
            })
        }

        override fun test(stack: ItemStack): Boolean = if (stack.isOf(HLItems.BEAKER))
            HTBeakerItem.getFluid(stack) == this.fluid && stack.count >= count
        else false

    }

    private class Packet(private val list: Collection<ItemStack>) : HTIngredient(
        list.toList().getOrNull(0)?.count ?: 1
    ) {

        override fun test(stack: ItemStack): Boolean =
            getMatchingStacks().any { ItemStack.areItemsEqual(stack, it) && stack.count >= count }

        override fun getMatchingStacks(): Collection<ItemStack> = list

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {

        }

    }

}