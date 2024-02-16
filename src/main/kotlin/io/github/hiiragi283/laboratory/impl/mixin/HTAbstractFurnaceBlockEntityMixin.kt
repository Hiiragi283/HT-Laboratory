package io.github.hiiragi283.laboratory.impl.mixin

import io.github.hiiragi283.laboratory.common.HLBlocks
import io.github.hiiragi283.laboratory.common.block.entity.HTAshCollectorBlockEntity
import io.github.hiiragi283.material.api.material.materials.HTCommonMaterials
import io.github.hiiragi283.material.api.part.HTPartManager
import io.github.hiiragi283.material.api.shape.HTShapes
import net.minecraft.block.BlockState
import net.minecraft.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

internal object HTAbstractFurnaceBlockEntityMixin {

    @JvmStatic
    fun tick(
        world: World,
        pos: BlockPos,
        state: BlockState,
        blockEntity: AbstractFurnaceBlockEntity,
        ci: CallbackInfo
    ) {
        val posDown: BlockPos = pos.down()
        val stateDown: BlockState = world.getBlockState(posDown)
        if (stateDown.isOf(HLBlocks.ASH_COLLECTOR)) {
            val ashCollector: HTAshCollectorBlockEntity =
                (world.getBlockEntity(posDown) as? HTAshCollectorBlockEntity) ?: return
            val stackIn: ItemStack = ashCollector.getStack(0)
            if (stackIn.isEmpty) {
                HTPartManager.getDefaultItem(HTCommonMaterials.ASHES, HTShapes.DUST)?.defaultStack?.let {
                    ashCollector.setStack(0, it)
                }
            } else if (stackIn.count < stackIn.maxCount) {
                stackIn.increment(1)
            }
        }
    }

}