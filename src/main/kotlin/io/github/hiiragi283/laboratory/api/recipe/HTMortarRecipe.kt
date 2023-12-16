package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonObject
import io.github.hiiragi283.laboratory.common.HLRecipes
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class HTMortarRecipe(
    private val id: Identifier,
    private val primary: HTIngredient,
    private val secondary: HTIngredient,
    private val output: ItemStack
) : HTRecipe<HTMortarRecipe> {

    //    HTRecipe    //

    override fun matches(world: World, pos: BlockPos): Boolean = false

    override fun craft(world: World, pos: BlockPos): ItemStack = output

    override fun getOutput(): ItemStack = output

    override fun getId(): Identifier = id

    override fun getSerializer(): RecipeSerializer<*> = HLRecipes.MORTAR_SERIALIZER

    override fun getType(): RecipeType<*> = HLRecipes.MORTAR_TYPE

    //    RecipeSerializer    //

    object Serializer : RecipeSerializer<HTMortarRecipe> {

        override fun read(id: Identifier, json: JsonObject): HTMortarRecipe {
            val primary: HTIngredient = HTIngredient.read(JsonHelper.getObject(json, "primary"))
            val secondary: HTIngredient = HTIngredient.read(JsonHelper.getObject(json, "secondary"))
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