package io.github.hiiragi283.laboratory.common.screen

import io.github.hiiragi283.laboratory.common.HLScreenHandlerTypes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class HTAshCollectorScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    private val inventory: Inventory = SimpleInventory(1)
) : ScreenHandler(HLScreenHandlerTypes.ASH_COLLECTOR, syncId) {

    init {
        checkSize(inventory, 1)
        inventory.onOpen(playerInventory.player)
        //Ash Slot
        addSlot(Slot(inventory, 0, 44 + 2 * 18, 20))
        //Player Inventory
        for (y in 0 until 3) {
            for (x in 0 until 9) {
                addSlot(Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, y * 18 + 51))
            }
        }
        //Player Hotbar
        for (x in 0 until 9) {
            addSlot(Slot(playerInventory, x, 8 + x * 18, 109))
        }
    }

    override fun canUse(player: PlayerEntity): Boolean = inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var result: ItemStack = ItemStack.EMPTY
        val slot: Slot = slots[index]
        if (slot.hasStack()) {
            val stackIn: ItemStack = slot.stack
            result = stackIn.copy()
            when {
                when {
                    index < inventory.size() -> !this.insertItem(
                        stackIn,
                        inventory.size(), slots.size, true
                    )

                    else -> !this.insertItem(
                        stackIn, 0,
                        inventory.size(), false
                    )
                } -> return ItemStack.EMPTY

                stackIn.isEmpty -> slot.stack = ItemStack.EMPTY
                else -> slot.markDirty()
            }
        }
        return result
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.onClose(player)
    }

}