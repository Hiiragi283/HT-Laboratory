package io.github.hiiragi283.laboratory.api.recipe

import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.world.World

interface HTRecipe<T : BlockEntity> : Recipe<Inventory> {

    fun matches(blockEntity: T): Boolean

    fun craft(blockEntity: T): ItemStack

    //    Recipe    //

    override fun matches(inventory: Inventory, world: World): Boolean {
        throw UnsupportedOperationException()
    }

    override fun craft(inventory: Inventory): ItemStack {
        throw UnsupportedOperationException()
    }

    override fun fits(width: Int, height: Int): Boolean = true

}