package regolith.resources.android

import android.content.res.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import regolith.resources.FileIdentifier
import regolith.resources.FileResources
import java.io.File
import java.io.InputStream

/**
 * Android implementation of [FileResources] via the android [Resources] class.
 */
class AndroidFileResources(
    private val resources: Resources,
): FileResources {
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
            is FileIdentifier.AppFile -> resources.openRawResource(requireInt(id).key)
            is FileIdentifier.Path -> File(location).inputStream()
            is FileIdentifier.Uri -> File(location).inputStream()
        }
    }
}
