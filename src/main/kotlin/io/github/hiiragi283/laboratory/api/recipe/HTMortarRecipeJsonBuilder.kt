package io.github.hiiragi283.laboratory.api.recipe

import com.google.gson.JsonObject
import io.github.hiiragi283.laboratory.common.HLRecipeSerializers
import io.github.hiiragi283.laboratory.common.toJson
import io.github.hiiragi283.material.common.util.prefix
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import java.util.function.Consumer

class HTMortarRecipeJsonBuilder private constructor(
    private val output: Item,
    override val count: Int
) : HTCraftingRecipeJsonBuilder {

    constructor(output: ItemConvertible, count: Int = 1) : this(output.asItem(), count)

    private lateinit var primary: HTIngredient

    fun setPrimaryInput(ingredient: HTIngredient): HTMortarRecipeJsonBuilder = also { primary = ingredient }

    private var secondary: HTIngredient = HTIngredient.empty()

    fun setSecondaryInput(ingredient: HTIngredient): HTMortarRecipeJsonBuilder = also { secondary = ingredient }

    //    CraftingRecipeJsonBuilder    //

    override val advancementBuilder: Advancement.Builder = Advancement.Builder.create()

    override fun criterion(name: String, conditions: CriterionConditions): CraftingRecipeJsonBuilder =
        also { this.advancementBuilder.criterion(name, conditions) }

    private var group: String? = null

    override fun group(group: String?): CraftingRecipeJsonBuilder = also { this.group = group }

    override fun getOutputItem(): Item = output

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
                primary,
                secondary,
                ItemStack(output, count),
                advancementBuilder,
                recipeId.prefix("recipes/")
            )
        )
    }

    //    RecipeJsonProvider    //

    class Provider(
        private val recipeId: Identifier,
        private val group: String?,
        private val primary: HTIngredient,
        private val secondary: HTIngredient,
        private val output: ItemStack,
        private val advancementBuilder: Advancement.Builder,
        private val advancementId: Identifier
    ) : RecipeJsonProvider {

        override fun serialize(json: JsonObject) {
            //Group
            group?.let { json.addProperty("group", it) }
            //Input
            primary.jsonConsumer { json.add("primary", it) }
            secondary.jsonConsumer { json.add("secondary", it) }
            //Output
            json.add("result", output.toJson())
        }

        override fun getRecipeId(): Identifier = recipeId

        override fun getSerializer(): RecipeSerializer<*> = HLRecipeSerializers.MORTAR_SERIALIZER

        override fun toAdvancementJson(): JsonObject = advancementBuilder.toJson()

        override fun getAdvancementId(): Identifier = advancementId

    }

}