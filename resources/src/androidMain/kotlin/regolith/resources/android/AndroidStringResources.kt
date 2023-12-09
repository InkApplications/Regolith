package regolith.resources.android

import android.content.res.Resources
import regolith.resources.ResourceIdentifier
import regolith.resources.StringResources
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Android implementation of [StringResources] via the android [Resources] class.
 */
class AndroidStringResources(
    private val resources: Resources,
): StringResources {
    override fun getString(id: ResourceIdentifier): String {
        requireInt(id)
        return resources.getString(id.key)
    }

    override fun getString(id: ResourceIdentifier, vararg params: Any): String {
        requireInt(id)
        return resources.getString(id.key, *params)
    }

    @OptIn(ExperimentalContracts::class)
    private fun requireInt(id: ResourceIdentifier) {
        contract {
            returns() implies (id is ResourceIdentifier.IdInt)
        }
        if (id !is ResourceIdentifier.IdInt) {
            throw IllegalArgumentException("Android resources require Integer IDs")
        }
    }
}
