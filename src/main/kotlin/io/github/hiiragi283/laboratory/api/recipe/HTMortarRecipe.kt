package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonObject
import io.github.hiiragi283.laboratory.common.HLRecipes
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class HTMortarRecipe(
    private val id: Identifier,
    private val group: String?,
    private val output: ItemStack,
    private val primary: HTIngredient,
    private val secondary: HTIngredient
) : HTRecipe<HTMortarRecipe> {

    //    HTRecipe    //

    override fun matches(world: World, pos: BlockPos): Boolean = false

    override fun craft(world: World, pos: BlockPos): ItemStack = output

    override fun getOutput(): ItemStack = output

    override fun getGroup(): String = group ?: super.getGroup()

    override fun getId(): Identifier = id

    override fun getSerializer(): RecipeSerializer<*> = HLRecipes.MORTAR_SERIALIZER

    override fun getType(): RecipeType<*> = HLRecipes.MORTAR_TYPE

    //    RecipeSerializer    //

    object Serializer : RecipeSerializer<HTMortarRecipe> {

        override fun read(id: Identifier, json: JsonObject): HTMortarRecipe {
            TODO("Not yet implemented")
        }

        override fun read(id: Identifier, buf: PacketByteBuf): HTMortarRecipe {
            TODO("Not yet implemented")
        }

        override fun write(buf: PacketByteBuf, recipe: HTMortarRecipe) {
            TODO("Not yet implemented")
        }

    }

}