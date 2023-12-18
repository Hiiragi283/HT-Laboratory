package io.github.hiiragi283.laboratory.compat.rei.displays

import io.github.hiiragi283.laboratory.common.HLBlocks
import io.github.hiiragi283.laboratory.common.block.HTMortarBlock
import io.github.hiiragi283.laboratory.compat.rei.HLReiPlugin
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.text.Text

object HTMortarCategory : DisplayCategory<HTMortarDisplay> {

    override fun getCategoryIdentifier(): CategoryIdentifier<HTMortarDisplay> = HLReiPlugin.MORTAR

    override fun getTitle(): Text = HTMortarBlock.TITLE

    override fun getIcon(): Renderer = EntryStacks.of(HLBlocks.MORTAR)

    override fun setupDisplay(display: HTMortarDisplay, bounds: Rectangle): MutableList<Widget> {
        val startPoint = Point(bounds.centerX - 31, bounds.centerY - 13)
        val widgets: MutableList<Widget> = mutableListOf()
        widgets += Widgets.createRecipeBase(bounds)
        widgets += Widgets.createArrow(Point(startPoint.x + 27, startPoint.y + 4))
        widgets += Widgets.createResultSlotBackground(Point(startPoint.x + 61, startPoint.y + 5))
        widgets += Widgets.createSlot(Point(startPoint.x + 4 - 22, startPoint.y + 5)).entries(display.inputEntries[0])
            .markInput()
        widgets += Widgets.createSlot(Point(startPoint.x + 4, startPoint.y + 5)).entries(display.inputEntries[1])
            .markInput()
        widgets += Widgets.createSlot(Point(startPoint.x + 61, startPoint.y + 5)).entries(display.outputEntries[0])
            .disableBackground().markOutput()
        return widgets
    }

    override fun getDisplayHeight(): Int = 36

}