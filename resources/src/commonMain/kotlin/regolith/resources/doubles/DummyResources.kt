package regolith.resources.doubles

import regolith.resources.ResourceIdentifier
import regolith.resources.StringResources

/**
 * Implements [StringResources] with placeholder/empty strings.
 *
 * This class can be useful for testing and delegation.
 */
object DummyResources: StringResources {
    override fun getString(id: ResourceIdentifier): String = ""
    override fun getString(id: ResourceIdentifier, vararg params: Any) = ""
}
