package regolith.resources.jvm

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import regolith.resources.FileIdentifier
import regolith.resources.FileResources
import regolith.resources.ResourceIdentifier
import java.io.File
import java.io.InputStream
import java.net.URI

/**
 * JVM file loading implementation.
 *
 * Note: This does not support ID [ResourceIdentifier] types and loads string
 * identifiers as Resource names.
 */
class JvmFileResources: FileResources {
    override suspend fun getFileContents(id: FileIdentifier): String {
        return withContext(Dispatchers.IO) {
            id.getInputStream().bufferedReader().readText()
        }
    }

    override fun getFileLines(id: FileIdentifier): Flow<String> {
        return id.getInputStream().bufferedReader().lineSequence().asFlow()
    }

    private fun FileIdentifier.getInputStream(): InputStream {
        return when (this) {
            is FileIdentifier.AppFile -> {
                val resourceId = id as? ResourceIdentifier.IdString ?: throw IllegalArgumentException("JVM does not support loading resources by ID.")
                Thread.currentThread().contextClassLoader.getResourceAsStream(resourceId.key) ?: throw IllegalArgumentException("Resource not found: $resourceId")
            }
            is FileIdentifier.Path -> {
                File(location).inputStream()
            }
            is FileIdentifier.Uri -> File(URI.create(location)).inputStream()
        }
    }
}
