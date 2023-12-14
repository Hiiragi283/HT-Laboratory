package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonObject
import io.github.hiiragi283.laboratory.common.HLRecipes
import io.github.hiiragi283.material.common.util.modify
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import java.util.function.Consumer

class HTMortarRecipeJsonBuilder(
    private val output: HTIngredient.Item,
    private val primary: HTIngredient,
    private val secondary: HTIngredient = HTIngredient.empty()
) : CraftingRecipeJsonBuilder {

    //    CraftingRecipeJsonBuilder    //

    private val advancementBuilder: Advancement.Builder = Advancement.Builder.create()

    override fun criterion(name: String, conditions: CriterionConditions): CraftingRecipeJsonBuilder =
        also { this.advancementBuilder.criterion(name, conditions) }

    private var group: String? = null

    override fun group(group: String?): CraftingRecipeJsonBuilder = also { this.group = group }

    override fun getOutputItem(): Item = output.item

    override fun offerTo(exporter: Consumer<RecipeJsonProvider>, recipeId: Identifier) {
        validate(recipeId)
        advancementBuilder
            .parent(Identifier("recipes/root"))
            .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
            .rewards(AdvancementRewards.Builder.recipe(recipeId))
            .criteriaMerger(CriterionMerger.OR)
        exporter.accept(
            Provider(
                recipeId,
                group,
                output,
                primary,
                secondary,
                advancementBuilder,
                recipeId.modify { "recipes/${output.item.group!!.name}/$it" })
        )
    }

    private fun validate(recipeId: Identifier) {
        check(advancementBuilder.criteria.isNotEmpty()) { "No way of obtaining recipe $recipeId" }
    }

    //    RecipeJsonProvider    //

    class Provider(
        private val recipeId: Identifier,
        private val group: String?,
        private val output: HTIngredient.Item,
        private val primary: HTIngredient,
        private val secondary: HTIngredient,
        private val advancementBuilder: Advancement.Builder,
        private val advancementId: Identifier
    ) : RecipeJsonProvider {

        override fun serialize(json: JsonObject) {
            //Group
            group?.let { json.addProperty("group", it) }
            //Input
            json.add("primary", primary.toJson())
            json.add("secondary", secondary.toJson())
            //Output
            json.add("result", output.toJson())
        }

        override fun getRecipeId(): Identifier = recipeId

        override fun getSerializer(): RecipeSerializer<*> = HLRecipes.MORTAR_SERIALIZER

        override fun toAdvancementJson(): JsonObject = advancementBuilder.toJson()

        override fun getAdvancementId(): Identifier = advancementId

    }

}