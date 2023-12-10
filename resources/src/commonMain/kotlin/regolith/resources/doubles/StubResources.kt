package regolith.resources.doubles

import kotlinx.coroutines.flow.Flow
import regolith.resources.FileIdentifier
import regolith.resources.FileResources
import regolith.resources.ResourceIdentifier
import regolith.resources.StringResources

/**
 * Implements the [StringResources] interface by throwing exceptions.
 *
 * This class can be useful for testing and delegation.
 */
object StubResources: StringResources, FileResources {
    override fun getString(id: ResourceIdentifier): String = TODO("stub")
    override fun getString(id: ResourceIdentifier, vararg params: Any) = TODO("stub")
    override suspend fun getFileContents(id: FileIdentifier): String = TODO("stub")
    override fun getFileLines(id: FileIdentifier): Flow<String> = TODO("stub")
}
