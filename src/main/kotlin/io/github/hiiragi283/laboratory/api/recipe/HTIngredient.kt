package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import io.github.hiiragi283.material.common.util.getEntries
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Consumer
import java.util.function.Predicate

sealed class HTIngredient(val count: Int) : Predicate<ItemStack> {

    abstract val matchingStacks: Collection<ItemStack>

    abstract fun jsonConsumer(consumer: Consumer<JsonObject>)

    fun write(buf: PacketByteBuf) {
        buf.writeCollection(matchingStacks, PacketByteBuf::writeItemStack)
    }

    companion object {

        @JvmStatic
        fun empty(): HTIngredient = Empty

        @JvmOverloads
        @JvmStatic
        fun ofItem(item: ItemConvertible, count: Int = 1) = Item(item.asItem(), count)

        @JvmOverloads
        @JvmStatic
        fun ofTag(tagKey: TagKey<net.minecraft.item.Item>, count: Int = 1) = Tag(tagKey, count)

        @JvmStatic
        fun read(json: JsonElement): HTIngredient {
            if (json.isJsonObject) {
                val jsonObject: JsonObject = json.asJsonObject
                val hasItem: Boolean = jsonObject.has("item")
                val hasTag: Boolean = jsonObject.has("tag")
                val hasCount: Boolean = jsonObject.has("count")
                if (hasItem && hasTag) {
                    throw JsonParseException("An ingredient entry is either a tag or an item, not both!")
                } else if (hasItem) {
                    val itemId = Identifier(jsonObject.getAsJsonPrimitive("item").asString)
                    val item: net.minecraft.item.Item = Registry.ITEM.get(itemId)
                    val count: Int = if (hasCount) jsonObject.get("count").asInt else 1
                    return Item(item, count)
                } else if (hasTag) {
                    val tagId = Identifier(jsonObject.getAsJsonPrimitive("tag").asString)
                    val tagKey: TagKey<net.minecraft.item.Item> = TagKey.of(Registry.ITEM_KEY, tagId)
                    val count: Int = if (hasCount) jsonObject.get("count").asInt else 1
                    return Tag(tagKey, count)
                } else throw JsonParseException("An ingredient entry needs either a tag or an item")
            } else throw JsonSyntaxException("HTIngredient must be JsonObject!")
        }

        @JvmStatic
        fun read(buf: PacketByteBuf): HTIngredient {
            val list: List<ItemStack> = buf.readList(PacketByteBuf::readItemStack)
            return if (list.isEmpty() ) empty() else Packet(list)
        }

    }

    data object Empty : HTIngredient(0) {

        override val matchingStacks: Collection<ItemStack> = listOf()

        override fun test(stack: ItemStack): Boolean = stack.isEmpty

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {

        }

    }

    class Item internal constructor(
        val item: net.minecraft.item.Item,
        count: Int
    ) : HTIngredient(count) {

        override val matchingStacks: Collection<ItemStack> = listOf(ItemStack(item, count))

        override fun test(stack: ItemStack): Boolean = stack.isOf(item) && stack.count >= count

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {
            consumer.accept(JsonObject().apply {
                addProperty("item", Registry.ITEM.getId(item).toString())
                addProperty("count", count)
            })
        }

        operator fun component1(): net.minecraft.item.Item = item

        operator fun component2(): Int = count

    }

    class Tag internal constructor(
        val tagKey: TagKey<net.minecraft.item.Item>,
        count: Int
    ) : HTIngredient(count) {

        override val matchingStacks: Collection<ItemStack> =
            tagKey.getEntries(Registry.ITEM).map { ItemStack(it, count) }

        override fun test(stack: ItemStack): Boolean = stack.isIn(tagKey) && stack.count >= count

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {
            consumer.accept(JsonObject().apply {
                addProperty("tag", tagKey.id.toString())
                addProperty("count", count)
            })
        }

        operator fun component1(): TagKey<net.minecraft.item.Item> = tagKey

        operator fun component2(): Int = count

    }

    class Packet(override val matchingStacks: Collection<ItemStack>) : HTIngredient(
        matchingStacks.toList().getOrNull(0)?.count ?: 1
    ) {

        override fun test(stack: ItemStack): Boolean = matchingStacks.any { ItemStack.areEqual(stack, it) }

        override fun jsonConsumer(consumer: Consumer<JsonObject>) {

        }

    }

}