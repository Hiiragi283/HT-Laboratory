package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonObject
import io.github.hiiragi283.laboratory.common.HLRecipeSerializers
import io.github.hiiragi283.laboratory.common.HLRecipeTypes
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.world.World

class HTMortarRecipe(
    @JvmField val id: Identifier,
    @JvmField val primary: HTIngredient,
    @JvmField val secondary: HTIngredient,
    @JvmField val output: ItemStack
) : Recipe<Inventory> {

    fun testSecondary(stack: ItemStack): Boolean = secondary.test(stack)

    //    HTRecipe    //

    override fun matches(inventory: Inventory, world: World): Boolean =
        primary.test(inventory.getStack(0)) && secondary.test(inventory.getStack(1))

    override fun craft(inventory: Inventory): ItemStack = output.copy()

    override fun fits(width: Int, height: Int): Boolean = true

    override fun getOutput(): ItemStack = output

    override fun getId(): Identifier = id

    override fun getSerializer(): RecipeSerializer<*> = HLRecipeSerializers.MORTAR_SERIALIZER

    override fun getType(): RecipeType<*> = HLRecipeTypes.MORTAR

    //    RecipeSerializer    //

    object Serializer : RecipeSerializer<HTMortarRecipe> {

        override fun read(id: Identifier, json: JsonObject): HTMortarRecipe {
            val primary: HTIngredient = HTIngredient.read(JsonHelper.getObject(json, "primary"))
            val secondary: HTIngredient = JsonHelper.getObject(json, "secondary", null)
                ?.let(HTIngredient.Companion::read)
                ?: HTIngredient.empty()
            val output: ItemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"))
            return HTMortarRecipe(id, primary, secondary, output)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): HTMortarRecipe {
            val primary: HTIngredient = HTIngredient.read(buf)
            val secondary: HTIngredient = HTIngredient.read(buf)
            val output: ItemStack = buf.readItemStack()
            return HTMortarRecipe(id, primary, secondary, output)
        }

        override fun write(buf: PacketByteBuf, recipe: HTMortarRecipe) {
            recipe.primary.write(buf)
            recipe.secondary.write(buf)
            buf.writeItemStack(recipe.output)
        }

    }

}