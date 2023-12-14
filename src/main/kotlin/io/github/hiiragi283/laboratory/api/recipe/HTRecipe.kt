package io.github.hiiragi283.laboratory.api.recipe

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface HTRecipe<T : HTRecipe<T>> : Recipe<Inventory> {

    fun matches(world: World, pos: BlockPos): Boolean

    fun craft(world: World, pos: BlockPos): ItemStack

    //    Recipe    //

    override fun matches(inventory: Inventory, world: World): Boolean {
        throw UnsupportedOperationException()
    }

    override fun craft(inventory: Inventory): ItemStack {
        throw UnsupportedOperationException()
    }

    override fun fits(width: Int, height: Int): Boolean = true

}