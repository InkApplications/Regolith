package regolith.resources.android

import android.content.res.Resources
import regolith.resources.ResourceIdentifier
import regolith.resources.StringResources

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
}
