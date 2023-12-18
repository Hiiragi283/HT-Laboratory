package io.github.hiiragi283.laboratory.compat.rei.displays

import io.github.hiiragi283.laboratory.api.recipe.HTMortarRecipe
import io.github.hiiragi283.laboratory.compat.rei.HLReiPlugin
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.minecraft.util.Identifier
import java.util.*

class HTMortarDisplay(
    inputs: List<EntryIngredient>,
    outputs: List<EntryIngredient>,
    location: Optional<Identifier>
) : BasicDisplay(inputs, outputs, location) {

    constructor(recipe: HTMortarRecipe) : this(
        listOf(
            EntryIngredients.ofItemStacks(recipe.primary.getMatchingStacks()),
            EntryIngredients.ofItemStacks(recipe.secondary.getMatchingStacks())
        ),
        listOf(EntryIngredients.of(recipe.output)),
        Optional.ofNullable(recipe.id)
    )

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = HLReiPlugin.MORTAR

}