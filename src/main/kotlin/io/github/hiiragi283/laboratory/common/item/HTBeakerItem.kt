package io.github.hiiragi283.laboratory.common.item

import io.github.hiiragi283.laboratory.common.HLItems
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry

/**
 * Reference: <a href="https://github.com/TechReborn/TechReborn/blob/1.18/src/main/java/techreborn/items/DynamicCellItem.java">TechReborn - GitHub</a>
 */

@Suppress("UnstableApiUsage")
object HTBeakerItem : Item(FabricItemSettings().group(ItemGroup.MISC)) {

    @JvmStatic
    fun getStack(fluid: Fluid, count: Int = 1): ItemStack = defaultStack.apply {
        this.orCreateNbt.putString("fluid", Registry.FLUID.getId(fluid).toString())
        setCount(count)
    }

    @JvmStatic
    fun getEmptyStack(count: Int = 1): ItemStack = defaultStack.apply { setCount(count) }

    @JvmStatic
    fun getFluid(stack: ItemStack): Fluid = getFluid(stack.nbt)

    @JvmStatic
    fun getFluid(nbt: NbtCompound?): Fluid = nbt
        ?.getString("fluid")
        ?.let(::Identifier)
        ?.let(Registry.FLUID::get)
        ?: Fluids.EMPTY

    //    Item    //

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (isIn(group)) {
            stacks.add(getEmptyStack())
            Registry.FLUID
                .filterIsInstance<FlowableFluid>()
                .filter { it.isStill(it.defaultState) }
                .map(::getStack)
                .forEach(stacks::add)
        }
    }

    override fun getName(stack: ItemStack): Text {
        val fluid: Fluid = getFluid(stack)
        val variant: FluidVariant = FluidVariant.of(fluid)
        val attribute: Text = FluidVariantAttributes.getName(variant)
        return TranslatableText(translationKey, attribute)
    }

    init {
        FluidStorage.ITEM.registerForItems(::BeakerStorage, this)
    }

    class BeakerStorage(context: ContainerItemContext) : SingleVariantItemStorage<FluidVariant>(context) {

        constructor(stack: ItemStack, context: ContainerItemContext) : this(context)

        override fun getResource(currentVariant: ItemVariant): FluidVariant =
            FluidVariant.of(getFluid(currentVariant.nbt))

        override fun getAmount(currentVariant: ItemVariant): Long =
            if (getResource(currentVariant).isBlank) 0 else FluidConstants.BUCKET

        override fun getCapacity(variant: FluidVariant): Long = FluidConstants.BUCKET

        override fun getBlankResource(): FluidVariant = FluidVariant.blank()

        override fun getUpdatedVariant(
            currentVariant: ItemVariant,
            newResource: FluidVariant,
            newAmount: Long
        ): ItemVariant = when {
            (newResource.isBlank || newAmount == 0.toLong()) -> ItemVariant.of(HLItems.BEAKER)
            (newAmount == FluidConstants.BUCKET) -> ItemVariant.of(getStack(newResource.fluid))
            else -> throw IllegalArgumentException()
        }

        override fun insert(insertedResource: FluidVariant, maxAmount: Long, transaction: TransactionContext): Long =
            if (isResourceBlank && maxAmount >= FluidConstants.BUCKET) super.insert(
                insertedResource,
                FluidConstants.BUCKET,
                transaction
            ) else 0

        override fun extract(extractedResource: FluidVariant, maxAmount: Long, transaction: TransactionContext): Long =
            if (!isResourceBlank && maxAmount >= FluidConstants.BUCKET) super.insert(
                extractedResource,
                FluidConstants.BUCKET,
                transaction
            ) else 0

    }

}