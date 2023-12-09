package regolith.resources.doubles

import regolith.resources.ResourceIdentifier
import regolith.resources.StringResources

/**
 * Implements [StringResources] by returning input parameters in the output.
 *
 * For a simple string, this will return just the ID of the resource given.
 * For example:
 *
 *     getString("Foo") // returns "Foo"
 *     getString(123) // returns "123"
 *
 * If the resource has parameters, the prameters are returned as a list
 * after the ID.
 * For example:
 *
 *     getString("Foo", "Bar", 123) // returns "Foo[Bar,123]"
 *     getString(123, "Bar", 456) // returns "123[Bar,456]"
 *
 * This class is useful for testing and verifying that ID/parameters are
 * as expected.
 */
object ParrotResources: StringResources {
    override fun getString(id: ResourceIdentifier): String = "$id"
    override fun getString(id: ResourceIdentifier, vararg params: Any) = "$id[${params.joinToString(",")}]"
}
