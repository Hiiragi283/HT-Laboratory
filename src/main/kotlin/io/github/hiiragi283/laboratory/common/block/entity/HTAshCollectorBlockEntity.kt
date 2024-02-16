package io.github.hiiragi283.laboratory.common.block.entity

import io.github.hiiragi283.laboratory.common.HLBlockEntityTypes
import io.github.hiiragi283.laboratory.common.block.HTAbstractCollectorBlock
import io.github.hiiragi283.laboratory.common.screen.HTAshCollectorScreenHandler
import net.minecraft.block.BlockState
import net.minecraft.block.entity.LockableContainerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

class HTAshCollectorBlockEntity(pos: BlockPos, state: BlockState) : LockableContainerBlockEntity(
    HLBlockEntityTypes.ASH_COLLECTOR,
    pos,
    state
) {

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

    private val inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(1, ItemStack.EMPTY)

    override fun clear() {
        inventory.clear()
    }

    override fun size(): Int = inventory.size

    override fun isEmpty(): Boolean = inventory.isEmpty()

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
        if (!stack.isEmpty && stack.count > maxCountPerStack) {
            stack.count = maxCountPerStack
        }
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean = true

    //    LockableContainerBlockEntity    //

    override fun getContainerName(): Text = HTAbstractCollectorBlock.getTitle()

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler =
        HTAshCollectorScreenHandler(syncId, playerInventory, this)

}