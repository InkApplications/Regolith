package regolith.resources.doubles

import regolith.resources.ResourceIdentifier
import regolith.resources.StringResources

/**
 * Implements the [StringResources] interface by throwing exceptions.
 *
 * This class can be useful for testing and delegation.
 */
object StubResources: StringResources {
    override fun getString(id: ResourceIdentifier): String = TODO("stub")
    override fun getString(id: ResourceIdentifier, vararg params: Any) = TODO("stub")
}
