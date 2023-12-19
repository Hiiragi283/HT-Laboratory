package io.github.hiiragi283.laboratory.compat

import io.github.hiiragi283.material.api.material.HTMaterial
import net.minecraft.util.Identifier

interface HTMaterialApiLookup<A, C> {

    fun find(material: HTMaterial, context: C): A?

    fun register(provider: HTMaterialApiProvider<A, C>, material: HTMaterial)

    fun interface HTMaterialApiProvider<A, C> {

        fun find(material: HTMaterial, context: C): A?

    }

    companion object {
        fun <A, C> get(
            id: Identifier,
            apiClass: Class<out A>,
            contextClass: Class<out C>
        ): HTMaterialApiLookupImpl<A, C> = HTMaterialApiLookupImpl.get(id, apiClass, contextClass)
    }

}