package io.github.hiiragi283.laboratory.common.screen

import io.github.hiiragi283.laboratory.api.recipe.HTIngredient
import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipe
import io.github.hiiragi283.laboratory.common.HLBlocks
import io.github.hiiragi283.laboratory.common.HLRecipeTypes
import io.github.hiiragi283.laboratory.common.HLScreenHandlerTypes
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ForgingScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.slot.Slot
import net.minecraft.world.World
import net.minecraft.world.WorldEvents

class HTMortarScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext = ScreenHandlerContext.EMPTY
) : ForgingScreenHandler(HLScreenHandlerTypes.MORTAR, syncId, playerInventory, context) {

    private val world: World = playerInventory.player.world

    private val recipes: MutableList<HTMortarRecipe> = world.recipeManager.listAllOfType(HLRecipeTypes.MORTAR)

    private var currentRecipe: HTMortarRecipe? = null

    //    ForgingScreenHandler    //

    override fun canUse(state: BlockState): Boolean = state.isOf(HLBlocks.MORTAR)

    override fun canTakeOutput(player: PlayerEntity, present: Boolean): Boolean =
        currentRecipe?.matches(input, world) == true

    override fun onTakeOutput(player: PlayerEntity, stack: ItemStack) {
        stack.onCraft(player.world, player, stack.count)
        output.unlockLastRecipe(player)
        decrementStack(0, currentRecipe!!.primary)
        decrementStack(1, currentRecipe!!.secondary)
        context.run { world, pos -> world.syncWorldEvent(WorldEvents.SMITHING_TABLE_USED, pos, 0) }
    }

    private fun decrementStack(slot: Int, ingredient: HTIngredient) {
        input.getStack(slot).run {
            this.decrement(ingredient.count)
            input.setStack(slot, this)
        }
    }

    override fun updateResult() {
        val list: MutableList<HTMortarRecipe> =
            world.recipeManager.getAllMatches(HLRecipeTypes.MORTAR, this.input, this.world)
        if (list.isEmpty()) {
            output.setStack(0, ItemStack.EMPTY)
        } else {
            this.currentRecipe = list[0]
            currentRecipe?.let {
                output.setStack(0, it.craft(this.input))
                output.lastRecipe = it
            }
        }
    }

    override fun isUsableAsAddition(stack: ItemStack): Boolean = recipes.any { it.testSecondary(stack) }

    override fun canInsertIntoSlot(stack: ItemStack, slot: Slot): Boolean =
        slot.inventory != output && super.canInsertIntoSlot(stack, slot)

}