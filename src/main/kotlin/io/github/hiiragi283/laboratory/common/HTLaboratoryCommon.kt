package io.github.hiiragi283.laboratory.common

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object HTLaboratoryCommon : ModInitializer {

    const val MOD_ID: String = "ht_laboratory"
    const val MOD_NAME: String = "HT Laboratory"

    private val logger = LoggerFactory.getLogger(MOD_NAME)

    override fun onInitialize() {
        HLBlockEntityTypes
        HLBlocks
        HLItems
        HLRecipeSerializers
        HLRecipeTypes
        HLScreenHandlerTypes
    }

    @JvmStatic
    fun id(path: String): Identifier = Identifier(MOD_ID, path)

}