package io.github.hiiragi283.laboratory.common.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

class HTMortarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(
    null,
    pos,
    state
), Inventory, NamedScreenHandlerFactory {

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, inventory)
    }

    override fun writeNbt(nbt: NbtCompound) {
        Inventories.writeNbt(nbt, inventory)
        super.writeNbt(nbt)
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener> = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt(): NbtCompound = createNbt()

    //    Inventory    //

    private val inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(3, ItemStack.EMPTY)

    override fun clear() {
        inventory.clear()
    }

    override fun size(): Int = inventory.size

    override fun isEmpty(): Boolean = inventory.all(ItemStack::isEmpty)

    override fun getStack(slot: Int): ItemStack = inventory[slot]

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val result: ItemStack = Inventories.splitStack(inventory, slot, amount)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(inventory, slot)

    override fun setStack(slot: Int, stack: ItemStack) {
        inventory[slot] = stack
        if (stack.count > stack.maxCount) {
            stack.count = stack.maxCount
        }
    }

    override fun markDirty() {

    }

    override fun canPlayerUse(player: PlayerEntity): Boolean = true

    //    NamedScreenHandlerFactory    //

    override fun createMenu(syncId: Int, inv: PlayerInventory?, player: PlayerEntity?): ScreenHandler? {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): Text = LiteralText("Mortar")

}