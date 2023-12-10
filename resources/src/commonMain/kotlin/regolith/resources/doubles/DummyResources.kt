package regolith.resources.doubles

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import regolith.resources.FileIdentifier
import regolith.resources.FileResources
import regolith.resources.ResourceIdentifier
import regolith.resources.StringResources

/**
 * Implements [StringResources] with placeholder/empty strings.
 *
 * This class can be useful for testing and delegation.
 */
object DummyResources: StringResources, FileResources {
    override fun getString(id: ResourceIdentifier): String = ""
    override fun getString(id: ResourceIdentifier, vararg params: Any) = ""
    override suspend fun getFileContents(id: FileIdentifier): String = ""
    override fun getFileLines(id: FileIdentifier): Flow<String> = flow {}
}
