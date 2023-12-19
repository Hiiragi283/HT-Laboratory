package io.github.hiiragi283.laboratory.compat

import io.github.hiiragi283.material.api.material.HTMaterial
import net.fabricmc.fabric.api.lookup.v1.custom.ApiLookupMap
import net.fabricmc.fabric.api.lookup.v1.custom.ApiProviderMap
import net.minecraft.util.Identifier

@Suppress("UNCHECKED_CAST")
class HTMaterialApiLookupImpl<A, C> private constructor(
    id: Identifier,
    apiClass: Class<out A>,
    contextClass: Class<out C>
) : HTMaterialApiLookup<A, C> {

    companion object {

        private val LOOKUPS: ApiLookupMap<HTMaterialApiLookupImpl<*, *>> =
            ApiLookupMap.create(::HTMaterialApiLookupImpl)

        fun <A, C> get(
            id: Identifier,
            apiClass: Class<out A>,
            contextClass: Class<out C>
        ): HTMaterialApiLookupImpl<A, C> =
            LOOKUPS.getLookup(id, apiClass, contextClass) as HTMaterialApiLookupImpl<A, C>

    }

    private val providerMap: ApiProviderMap<HTMaterial, HTMaterialApiLookup.HTMaterialApiProvider<A, C>> = ApiProviderMap.create()

    override fun find(material: HTMaterial, context: C): A? = providerMap.get(material)?.find(material, context)

    override fun register(provider: HTMaterialApiLookup.HTMaterialApiProvider<A, C>, material: HTMaterial) {
        if (providerMap.putIfAbsent(material, provider) !== null) {

        }
    }

}